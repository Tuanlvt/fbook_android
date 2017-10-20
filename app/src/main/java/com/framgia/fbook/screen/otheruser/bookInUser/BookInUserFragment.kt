package com.framgia.fbook.screen.otheruser.bookInUser

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.ActionBookDetail
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Owner
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.FragmentBookInUserBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.bookdetail.BookDetailActivity
import com.framgia.fbook.screen.otheruser.OtherUserActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * BookInUser Screen.
 */
open class BookInUserFragment : BaseFragment(), BookInUserContract.ViewModel, ItemBookInUserClickListener, OnReturnBookListener {

  companion object {
    val TAG: String? = BookInUserFragment::class.java.name
    private val BOOK_IN_USER_PROFILE: String = "BOOK_IN_USER_PROFILE"
    private val USER_ID: String = "USER_ID"
    private val POSITION_TAB = "POSITION_TAB"
    fun newInstance(type: String?, userId: Int?, positionTab: Int?): BookInUserFragment {
      val fragment = BookInUserFragment()
      val bundle = Bundle()
      bundle.putString(BOOK_IN_USER_PROFILE, type)
      userId?.let { bundle.putInt(USER_ID, it) }
      positionTab?.let { bundle.putInt(POSITION_TAB, it) }
      fragment.arguments = bundle
      return fragment
    }
  }

  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mUserRepository: UserRepository
  @Inject
  internal lateinit var mPresenter: BookInUserContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mAdapter: BookInUserAdapter
  private lateinit var mBinding: FragmentBookInUserBinding
  var mBook: List<Book> = ArrayList()
  val mIsVisibleLayoutNoData: ObservableField<Boolean> = ObservableField(false)
  val mIsRefresh: ObservableField<Boolean> = ObservableField(false)
  var mUserId: Int? = 0
  var mTypeRequest: String? = ""

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerBookInUserComponent.builder()
        .otherUserComponent((activity as OtherUserActivity).getOtherUserComponent())
        .bookInUserModule(BookInUserModule(this))
        .build()
        .inject(this)

    mBinding = DataBindingUtil.inflate<FragmentBookInUserBinding>(inflater,
        R.layout.fragment_book_in_user, container, false)
    mBinding.viewModel = this
    fillData()
    return mBinding.root
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onGetBookInUserProfileSuccess(book: List<Book>) {
    if (book.isNotEmpty()) {
      mBook = book
      mAdapter.updateData(mBook)
      mIsVisibleLayoutNoData.set(false)
    } else {
      mIsVisibleLayoutNoData.set(true)
    }
    mIsRefresh.set(false)
  }

  override fun onReturnBookSuccess() {
    mPresenter.getBookInUserProfile(mUserId, mTypeRequest)
    mDialogManager.showSnackBarNoActionBar(mBinding.root, R.string.return_book_success)
  }

  override fun onItemBookInUserClick(book: Book?) {
    val bundle = Bundle()
    book?.id?.let {
      bundle.putInt(Constant.BOOK_DETAIL_EXTRA, it)
    }
    mNavigator.startActivity(BookDetailActivity::class.java, bundle)
  }

  override fun onShowProgresDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onError(exception: BaseException) {
    Log.d(TAG, exception.getMessageError())
    mDialogManager.showSnackBarTitleString(mBinding.root, exception.getMessageError())
    mIsRefresh.set(false)
  }

  override fun onReturnBookClick(book: Book?) {
    val actionBookDetail = ActionBookDetail()
    val mOwners = arrayListOf<Owner>()
    val ownerNames: MutableList<String?> = mutableListOf()
    val RETURN_BOOK = 3
    actionBookDetail.status = RETURN_BOOK

    book?.owners?.let { mOwners.addAll(it) }
    actionBookDetail.bookId = book?.id
    mOwners.mapTo(ownerNames) { it.name }

    mDialogManager.dialogListSingleChoice(getString(R.string.do_you_want_to_return_this_book),
        ownerNames, 0, MaterialDialog.ListCallbackSingleChoice({ _, _, position, _ ->
      val currentOwnerId = (mOwners[position]).id
      if (mUserId == currentOwnerId) {
        mDialogManager.dialogError(getString(R.string.you_are_the_owner_this_book))
      } else {
        actionBookDetail.ownerId = currentOwnerId
        mPresenter.returnBook(actionBookDetail)
      }
      true
    }))
  }

  override fun isNotRefresh(): Boolean {
    return mIsRefresh.get()
  }

  fun fillData() {
    mTypeRequest = arguments.getString(BOOK_IN_USER_PROFILE)
    mUserId = arguments.getInt(USER_ID)
    val positionTab: Int? = arguments.getInt(POSITION_TAB)
    mPresenter.getBookInUserProfile(mUserId, mTypeRequest)
    mIsVisibleLayoutNoData.set(true)
    mAdapter.setItemBookInUserClickListener(this)
    mAdapter.setReturnBookListener(this)
    mAdapter.getCheckCurrentUser(mUserId == mUserRepository.getUserLocal()?.id)
    positionTab?.let { mAdapter.getPositionTab(it) }
  }

  fun onClickReloadData(view: View) {
    fillData()
  }

  fun onRefresh() {
    mIsRefresh.set(true)
    mPresenter.getBookInUserProfile(mUserId, mTypeRequest)
  }
}
