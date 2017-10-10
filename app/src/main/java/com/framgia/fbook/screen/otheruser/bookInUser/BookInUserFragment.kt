package com.framgia.fbook.screen.otheruser.bookInUser

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.FragmentBookInUserBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.bookdetail.BookDetailActivity
import com.framgia.fbook.screen.otheruser.OtherUserActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import javax.inject.Inject

/**
 * BookInUser Screen.
 */
class BookInUserFragment : BaseFragment(), BookInUserContract.ViewModel, ItemBookInUserClickListener, OnReturnBookListener {
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
  internal lateinit var mAdapter: BookInUserAdapter
  var mBook: List<Book> = ArrayList()
  var mUserId: Int? = 0
  var mTypeRequest: String? = ""

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerBookInUserComponent.builder()
        .otherUserComponent((activity as OtherUserActivity).getOtherUserComponent())
        .bookInUserModule(BookInUserModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentBookInUserBinding>(inflater,
        R.layout.fragment_book_in_user, container, false)
    binding.viewModel = this
    fillData()
    return binding.root
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
    mBook = book
    mAdapter.updateData(mBook)
  }

  override fun onReturnBookSuccess() {
    mPresenter.getBookInUserProfile(mUserId, mTypeRequest)
  }

  override fun onItemBookInUserClick(book: Book?) {
    val bundle = Bundle()
    bundle.putParcelable(Constant.BOOK_DETAIL_EXTRA, book)
    mNavigator.startActivity(BookDetailActivity::class.java, bundle)
  }

  override fun onError(exception: BaseException) {
    Log.d(TAG, exception.getMessageError())
  }

  override fun onReturnBookClick() {
    //Todo Edit Later
  }

  fun fillData() {
    mTypeRequest = arguments.getString(BOOK_IN_USER_PROFILE)
    mUserId = arguments.getInt(USER_ID)
    val positionTab: Int? = arguments.getInt(POSITION_TAB)
    mPresenter.getBookInUserProfile(mUserId, mTypeRequest)
    mAdapter.setItemBookInUserClickListener(this)
    positionTab?.let { mAdapter.getPositionTab(it) }
  }
}
