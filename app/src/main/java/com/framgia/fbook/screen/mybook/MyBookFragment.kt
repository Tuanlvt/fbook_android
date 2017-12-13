package com.framgia.fbook.screen.mybook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.FragmentMybookBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.approverequest.ApproveRequestActivity
import com.framgia.fbook.screen.bookdetail.BookDetailActivity
import com.framgia.fbook.screen.login.LoginActivity
import com.framgia.fbook.screen.main.LoginListener
import com.framgia.fbook.screen.main.MainActivity
import com.framgia.fbook.screen.main.NotificationListener
import com.framgia.fbook.screen.updatebook.UpdateBookActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * MyBook Screen.
 */
open class MyBookFragment : BaseFragment(), MyBookContract.ViewModel, ItemMyBookClickListener, LoginListener.LoginOnMyBook {

  @Inject
  internal lateinit var mPresenter: MyBookContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mMyBookAdapter: MyBookAdapter
  @Inject
  internal lateinit var mUserRepository: UserRepository
  @Inject
  internal lateinit var mNavigator: Navigator
  private var mTimeClick: Long = 0
  private var mIsLoadDataFirstTime: Boolean = true
  private lateinit var mUpdateNotificationListener: NotificationListener.UpdateNotificationListener

  val mIsVisiableLayoutNodata: ObservableField<Boolean> = ObservableField()
  val mIsVisibleLayoutNotLoggedIn: ObservableField<Boolean> = ObservableField()
  val mIsRefresh: ObservableField<Boolean> = ObservableField(false)
  val mIsEnable: ObservableField<Boolean> = ObservableField(false)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerMyBookComponent.builder()
        .mainComponent((activity as MainActivity).getMainComponent())
        .myBookModule(MyBookModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentMybookBinding>(inflater, R.layout.fragment_mybook,
        container, false)
    binding.viewModel = this

    mMyBookAdapter.setItemMyBookListener(this)
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

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is MainActivity) {
      mUpdateNotificationListener = context
      context.setLoginOnMyBookListener(this)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK && requestCode == Constant.RequestCode.TAB_MY_BOOK_REQUEST) {
      loggedIn()
    }
  }

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (!isVisibleToUser) {
      return
    }
    if (mIsLoadDataFirstTime) {
      val user = mUserRepository.getUserLocal()
      if (user == null) {

        mDialogManager.dialogBasic(getString(R.string.inform),
            getString(R.string.you_must_be_login_into_perform_this_function),
            MaterialDialog.SingleButtonCallback { _, _ ->
              mNavigator.startActivityForResultFromFragment(LoginActivity::class.java,
                  Constant.RequestCode.TAB_MY_BOOK_REQUEST)
            })

        mIsVisibleLayoutNotLoggedIn.set(true)
        return
      }
      mIsEnable.set(true)
      mIsVisibleLayoutNotLoggedIn.set(false)
      user.let { mPresenter.getMyBook(userId = user.id) }
    }
  }

  override fun editClickListener(book: Book?) {
    val bundle = Bundle()
    bundle.putParcelable(Constant.BOOK_UPDATE_EXTRA, book)
    if (System.currentTimeMillis() > mTimeClick + Constant.TIME_DELAY_ONE_SECONDS) {
      mNavigator.startActivity(UpdateBookActivity::class.java, bundle)
      mTimeClick = System.currentTimeMillis()
    }
  }

  override fun onLoggedIn() {
    loggedIn()
  }

  override fun onError(e: BaseException) {
    mIsLoadDataFirstTime = false
    Log.e(TAG, e.getMessageError())
    mIsRefresh.set(false)
  }

  override fun onGetMyBookSuccess(listBook: List<Book>?) {
    mIsLoadDataFirstTime = false
    mIsRefresh.set(false)
    listBook?.let {
      mMyBookAdapter.updateData(it)
      checkSizeListBook(listBook.size)
    }
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun isNotRefresh(): Boolean {
    return mIsRefresh.get()
  }

  override fun onItemMyBookClick(book: Book) {
    val bundle = Bundle()
    book.id?.let {
      bundle.putInt(Constant.BOOK_DETAIL_EXTRA, it)
    }
    mNavigator.startActivity(BookDetailActivity::class.java, bundle)
  }

  override fun onClickViewAllRequest() {
    mNavigator.startActivity(ApproveRequestActivity::class.java)
  }

  private fun checkSizeListBook(size: Int?) {
    if (size == 0) {
      mIsVisiableLayoutNodata.set(true)
      return
    }
    mIsVisiableLayoutNodata.set(false)
  }

  fun onClickReloadData(view: View) {
    mPresenter.getMyBook(mUserRepository.getUserLocal()?.id)
  }

  fun onRefresh() {
    mIsRefresh.set(true)
    mPresenter.getMyBook(mUserRepository.getUserLocal()?.id)
  }

  private fun loggedIn() {
    mIsVisibleLayoutNotLoggedIn.set(false)
    mIsEnable.set(true)
    mUpdateNotificationListener.updateNotification()
    mPresenter.getMyBook(mUserRepository.getUserLocal()?.id)
  }

  companion object {

    val TAG: String = MyBookFragment::class.java.name

    fun newInstance(): MyBookFragment {
      return MyBookFragment()
    }
  }
}
