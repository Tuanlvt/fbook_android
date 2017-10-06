package com.framgia.fbook.screen.main

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.model.Notification
import com.framgia.fbook.databinding.ActivityMainBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.screen.SearchBook.SearchBookActivity
import com.framgia.fbook.screen.login.LoginActivity
import com.framgia.fbook.screen.notification.notificationFollow.NotificationFollowListener
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.framia.fbook.screen.main.MainContract
import com.roughike.bottombar.BottomBar
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.ViewModel, NotificationListener {

  private val DELAY_TIME_TWO_TAP_BACK_BUTTON = 2000
  private val PAGE_LIMIT = 3

  @Inject
  lateinit var presenter: MainContract.Presenter
  @Inject
  lateinit var mNavigator: Navigator
  @Inject
  lateinit var mAdapter: MainContainerPagerAdapter
  @Inject
  internal lateinit var mUserRepository: UserRepository
  lateinit var mMainComponent: MainComponent
  val mCurrentTab: ObservableField<Int> = ObservableField()
  val mPageLimit: ObservableField<Int> = ObservableField(PAGE_LIMIT)
  private var mIsDoubleTapBack = false
  private lateinit var mHandler: Handler
  private lateinit var mRunnable: Runnable
  val mAvatar: ObservableField<String> = ObservableField()
  val mIsVisibleAvatar: ObservableField<Boolean> = ObservableField()
  private lateinit var mNotificationFollowListener: NotificationFollowListener

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mMainComponent = DaggerMainComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .mainModule(MainModule(this))
        .build()
    mMainComponent.inject(this)

    val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    binding.viewModel = this
    mHandler = Handler()
    mRunnable = Runnable { mIsDoubleTapBack = false }
    onSelectItemMenu()
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun onStop() {
    presenter.onStop()
    super.onStop()
  }

  override fun onResume() {
    super.onResume()
    setAvatar()
  }

  fun setNotificationFollowListener(notificationFollowListener: NotificationFollowListener) {
    mNotificationFollowListener = notificationFollowListener
  }

  fun setCurrentTab(tab: Int) {
    mCurrentTab.set(tab)
  }

  private fun isBackClick(): Boolean {
    val fragment = mAdapter.getCurrentFragment()
    if (fragment is MainContainerFragment) {
      return fragment.onBackPressed()
    }
    return false
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

  fun onSelectItemMenu() {
    val bottomBar = findViewById(R.id.bottom_navigation) as BottomBar
    bottomBar.setOnTabSelectListener { idView ->
      when (idView) {
        R.id.tab_home -> setCurrentTab(Constant.Tab.TAB_HOME)
        R.id.tab_my_book -> setCurrentTab(Constant.Tab.TAB_MY_BOOK)
        R.id.tab_notification -> setCurrentTab(Constant.Tab.TAB_NOTIFICATION)
        R.id.tab_account -> setCurrentTab(Constant.Tab.TAB_ACCOUNT)
      }
      setVisibleAvatar(idView)
    }
  }

  fun onClickSearch(view: View) {
    mNavigator.startActivity(SearchBookActivity::class.java)
  }

  fun onClickChooseDomain(view: View) {
    //Todo dev later
  }

  override fun getNotificationFollow(notification: Notification?) {
    mNotificationFollowListener.getNotificationFollow(notification)
  }

  fun getMainComponent(): MainComponent {
    return mMainComponent
  }

  fun onClickLogin(view: View) {
    mNavigator.startActivity(LoginActivity::class.java)
  }

  fun setVisibleAvatar(idView: Int?) {
    if (idView == R.id.tab_account) {
      mIsVisibleAvatar.set(false)
      return
    }
    mIsVisibleAvatar.set(true)
  }

  fun setAvatar() {
    val user: User? = mUserRepository.getUserLocal()
    mAvatar.set(user?.avatar)
  }
}
