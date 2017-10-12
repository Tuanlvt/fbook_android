package com.framgia.fbook.screen.main

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Notification
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.ActivityMainBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.screen.SearchBook.SearchBookActivity
import com.framgia.fbook.screen.notification.notificationFollow.NotificationFollowListener
import com.framgia.fbook.screen.notification.notificationUser.NotificationUserListener
import com.framgia.fbook.screen.profile.ProfileActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.framia.fbook.screen.main.MainContract
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.roughike.bottombar.BottomBar
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.ViewModel, NotificationListener, NotificationListener.UserListener {

  private val TAG: String = MainActivity::class.java.name
  private val DELAY_TIME_TWO_TAP_BACK_BUTTON = 2000
  private val PAGE_LIMIT = 3

  @Inject
  lateinit var presenter: MainContract.Presenter
  @Inject
  lateinit var mNavigator: Navigator
  @Inject
  lateinit var mAdapter: MainContainerPagerAdapter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mUserRepository: UserRepository

  private lateinit var mMainComponent: MainComponent
  private lateinit var mHandler: Handler
  private lateinit var mRunnable: Runnable
  private lateinit var mNotificationFollowListener: NotificationFollowListener
  private lateinit var mListBookMainPageListener: ListBookMainPageListener
  private lateinit var mListBookSeeMoreListener: ListBookSeeMoreListener
  private lateinit var mNotificationUserListener: NotificationUserListener

  private var mIsDoubleTapBack = false
  private var mListOffices = mutableListOf<Office>()
  private var mCurrentOfficePosition: Int = 0
  private var mOfficeId: Int? = null
  private lateinit var mBottomBar: BottomBar

  val mCurrentTab: ObservableField<Int> = ObservableField()
  val mPageLimit: ObservableField<Int> = ObservableField(PAGE_LIMIT)
  val mAvatar: ObservableField<String> = ObservableField()
  val mIsVisibleAvatar: ObservableField<Boolean> = ObservableField()
  val mCurrentOffice: ObservableField<String> = ObservableField()
  var mUser: User? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mMainComponent = DaggerMainComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .mainModule(MainModule(this))
        .build()
    mMainComponent.inject(this)

    val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    binding.viewModel = this
    initData()
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun onStop() {
    presenter.onStop()
    mHandler.removeCallbacks(mRunnable)
    super.onStop()
  }

  override fun onResume() {
    super.onResume()
    checkDataUser()
  }

  override fun onBackPressed() {
    if (isBackClick()) {
      return
    }
    if (mIsDoubleTapBack) {
      super.onBackPressed()
      return
    }
    mIsDoubleTapBack = true
    Toast.makeText(this, getString(R.string.please_click_back_again_to_exit),
        Toast.LENGTH_SHORT).show()
    mHandler.postDelayed(mRunnable, DELAY_TIME_TWO_TAP_BACK_BUTTON.toLong())
  }

  override fun onGetCountNotificationSuccess(count: Int?) {
    count?.let {
      val notificationTab = mBottomBar.getTabWithId(R.id.tab_notification)
      notificationTab.setBadgeCount(count)
    }
  }

  override fun getNotificationFollow(notification: Notification?) {
    mNotificationFollowListener.getNotificationFollow(notification)
  }

  override fun getEnable(enable: Boolean) {
    mNotificationUserListener.getEnable(enable)
  }

  override fun onGetOfficeSuccess(listOffice: List<Office>?) {
    listOffice?.let {
      mListOffices.addAll(it)
      setCurrentOffice(it)
    }
  }

  override fun onError(baseException: BaseException) {
    Log.e(TAG, baseException.getMessageError())
  }

  private fun initData() {
    onSelectItemMenu()
    presenter.getOffices()
    presenter.getCountNotification()
    mHandler = Handler()
    mRunnable = Runnable { mIsDoubleTapBack = false }
  }

  private fun checkDataUser() {
    if (mUser != null) {
      return
    }
    mUser = mUserRepository.getUserLocal()
    mOfficeId = mUser?.officeId
    setAvatar(mUser?.avatar)
    setCurrentOffice(mListOffices)
  }

  fun setNotificationFollowListener(notificationFollowListener: NotificationFollowListener) {
    mNotificationFollowListener = notificationFollowListener
  }

  fun setNotificationUserListener(notificationUserListener: NotificationUserListener) {
    mNotificationUserListener = notificationUserListener
  }

  private fun setCurrentTab(tab: Int) {
    mCurrentTab.set(tab)
  }

  private fun isBackClick(): Boolean {
    val fragment = mAdapter.getCurrentFragment()
    if (fragment is MainContainerFragment) {
      return fragment.onBackPressed()
    }
    return false
  }

  private fun onSelectItemMenu() {
    mBottomBar = findViewById(R.id.bottom_navigation) as BottomBar
    mBottomBar.setOnTabSelectListener { idView ->
      when (idView) {
        R.id.tab_home -> setCurrentTab(Constant.Tab.TAB_HOME)
        R.id.tab_my_book -> setCurrentTab(Constant.Tab.TAB_MY_BOOK)
        R.id.tab_notification -> setCurrentTab(Constant.Tab.TAB_NOTIFICATION)
        R.id.tab_account -> setCurrentTab(Constant.Tab.TAB_ACCOUNT)
      }
      setVisibleAvatar(idView)
    }
  }

  fun getMainComponent(): MainComponent {
    return mMainComponent
  }

  private fun setVisibleAvatar(idView: Int?) {
    if (idView == R.id.tab_account) {
      mIsVisibleAvatar.set(false)
      return
    }
    mIsVisibleAvatar.set(true)
  }

  private fun setAvatar(avatar: String?) {
    mAvatar.set(avatar)
  }

  private fun setCurrentOffice(listOffice: List<Office>?) {
    if (mUser == null) {
      //TODO edit later
      return
    }
    listOffice?.let {
      for (office in it) {
        if (mUser?.officeId == office.id) {
          mCurrentOffice.set(office.name)
          mCurrentOfficePosition = it.indexOf(office)
          break
        }
      }
    }
  }

  fun setListBookMainPageListener(listBookMainPageListener: ListBookMainPageListener) {
    mListBookMainPageListener = listBookMainPageListener
  }

  fun setListBookSeeMoreListener(listBookSeeMoreListener: ListBookSeeMoreListener) {
    mListBookSeeMoreListener = listBookSeeMoreListener
  }

  private fun updateCurrentOffice(position: Int) {
    mCurrentOffice.set(mListOffices[position].name)
  }

  private fun showDialogListOffice() {
    val officeNames: MutableList<String?> = mutableListOf()
    mListOffices.mapTo(officeNames) { it.name }

    mDialogManager.dialogListSingleChoice(getString(R.string.select_office), officeNames,
        mCurrentOfficePosition,
        MaterialDialog.ListCallbackSingleChoice { _, _, position, _ ->
          mCurrentOfficePosition = position
          updateCurrentOffice(position)

          mOfficeId = mListOffices[position].id
          mListBookMainPageListener.onGetListBook(mOfficeId)
          val isShowPrevious = mAdapter.getCurrentFragment().childFragmentManager.backStackEntryCount > 1
          if (isShowPrevious) {
            mListBookSeeMoreListener.onGetListBook(mOfficeId)
          }
          true
        })
  }

  fun onClickAvatar(view: View) {
    if (mUser == null) {
      return
    }
    mNavigator.startActivity(ProfileActivity::class.java)
  }

  fun onClickSearch(view: View) {
    mNavigator.startActivity(SearchBookActivity::class.java)
  }

  fun onClickChooseWorkSpace(view: View) {
    if (mUser == null) {
      return
    }
    showDialogListOffice()
  }

  fun getCurrentOfficeId(): Int? {
    return mOfficeId
  }

  interface ListBookMainPageListener {
    fun onGetListBook(officeId: Int?)
  }

  interface ListBookSeeMoreListener {
    fun onGetListBook(officeId: Int?)
  }
}
