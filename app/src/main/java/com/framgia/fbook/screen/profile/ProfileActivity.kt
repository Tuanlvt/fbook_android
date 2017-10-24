package com.framgia.fbook.screen.profile;

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.util.Log
import android.view.View
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Follow
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.FollowOrUnFollowUserRequest
import com.framgia.fbook.databinding.ActivityProfileBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.screen.otheruser.OtherUserActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * Profile Screen.
 */
open class ProfileActivity : BaseActivity(), ProfileContract.ViewModel {

  private val mNavigator: Navigator by lazy { Navigator(this) }
  @Inject
  internal lateinit var mPresenter: ProfileContract.Presenter
  @Inject
  internal lateinit var profileAdapter: ProfileAdapter
  @Inject
  internal lateinit var mUserRepository: UserRepository
  @Inject
  internal lateinit var mDialogManager: DialogManager
  private lateinit var profileComponent: ProfileComponent
  private lateinit var mGetUserListenerPersonal: GetUserListener.onGetUserPersonal
  private lateinit var mGetUserListenerCategory: GetUserListener.onGetUserCategory
  private lateinit var mGetUserListenerFollow: GetUserListener.onGetFollowUser

  val mUser: ObservableField<User> = ObservableField()
  val mFollow: ObservableField<Follow> = ObservableField()
  val mPageLimit: ObservableField<Int> = ObservableField(PAGE_LIMIT)
  val mIsVisibleButtonFollow: ObservableField<Boolean> = ObservableField()
  val mIsVisibleLayoutButtonFollow: ObservableField<Boolean> = ObservableField(true)
  val mIsVisibleLayoutNoData: ObservableField<Boolean> = ObservableField(false)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    profileComponent = DaggerProfileComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .profileModule(ProfileModule(this))
        .build()
    profileComponent.inject(this)
    val binding = DataBindingUtil.setContentView<ActivityProfileBinding>(this,
        R.layout.activity_profile)
    binding.viewModel = this
    fillData()
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onBackPressed() {
    mNavigator.finishActivity()
  }

  fun setGetUserListenerPersonal(getUserListenerPersonal: GetUserListener.onGetUserPersonal) {
    mGetUserListenerPersonal = getUserListenerPersonal
  }

  fun setGetUserListenerCategory(getUserListenerCategory: GetUserListener.onGetUserCategory) {
    mGetUserListenerCategory = getUserListenerCategory
  }

  fun setGetUserListenerFollow(getUserListenerFollow: GetUserListener.onGetFollowUser) {
    mGetUserListenerFollow = getUserListenerFollow
  }

  override fun onGetUserOtherProfileSuccess(user: User?) {
    user?.let {
      mUser.set(user)
      mGetUserListenerPersonal.onGetUser(user)
      mGetUserListenerCategory.onGetUser(user)
    }
    mIsVisibleLayoutNoData.set(false)
    mDialogManager.dismissProgressDialog()
  }

  override fun onGetFollowInfomationOfUserSuccess(follow: Follow?) {
    follow?.let { mFollow.set(follow) }
    mIsVisibleButtonFollow.set(mFollow.get().isFollow)
    mGetUserListenerFollow.onGetFollow(follow)
  }

  override fun onError(exception: BaseException) {
    Log.e(TAG, exception.getMessageError())
    mDialogManager.dismissProgressDialog()
  }

  override fun onFollowOrUnFollowSuccess() {
    mIsVisibleButtonFollow.set(!mFollow.get().isFollow)
    mPresenter.getFollowInfomationOfUser(mUser.get().id)
  }

  fun onClickOther() {
    val bundle = Bundle()
    val idUser: Int? = mUser.get().id
    if (idUser != null) {
      bundle.putInt(Constant.BOOK_DETAIL_IN_USER_EXTRA, idUser)
      mNavigator.startActivity(OtherUserActivity::class.java, bundle)
    }
  }

  fun fillData() {
    val idUser: Int? = intent.getIntExtra(Constant.BOOK_DETAIL_EXTRA, 0)
    if (idUser != mUserRepository.getUserLocal()?.id && idUser != Constant.EXTRA_ZERO) {
      mPresenter.getUserOtherProfile(idUser)
      mPresenter.getFollowInfomationOfUser(idUser)
      mDialogManager.showIndeterminateProgressDialog()
      mIsVisibleLayoutNoData.set(true)
      return
    }
    mIsVisibleLayoutButtonFollow.set(false)
    mUser.set(mUserRepository.getUserLocal())
    mPresenter.getFollowInfomationOfUser(mUser.get().id)
    return
  }

  fun onClickArrowBack() {
    mNavigator.finishActivity()
  }

  fun onClickReloadData(view: View) {
    fillData()
  }

  fun onClickFollowOrUnFollowUser(view: View) {
    val followOrUnFollowUserRequest = FollowOrUnFollowUserRequest()
    val itemFollowOrUnFollowUserRequest = FollowOrUnFollowUserRequest.Item()

    itemFollowOrUnFollowUserRequest.userId = mUser.get().id
    followOrUnFollowUserRequest.item = itemFollowOrUnFollowUserRequest
    mPresenter.followOrUnFollow(followOrUnFollowUserRequest)
  }

  companion object {
    private val PAGE_LIMIT = 1
    val TAG: String = ProfileActivity::class.java.name
    fun newInstance(): ProfileActivity {
      return ProfileActivity()
    }
  }

  fun getProfileComponent(): ProfileComponent {
    return profileComponent
  }
}
