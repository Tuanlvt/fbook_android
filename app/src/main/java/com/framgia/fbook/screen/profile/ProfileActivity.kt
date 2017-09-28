package com.framgia.fbook.screen.profile;

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.util.Log
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.ActivityProfileBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.screen.bookdetail.BookDetailActivity
import com.framgia.fbook.screen.otheruser.OtherUserActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * Profile Screen.
 */
class ProfileActivity : BaseActivity(), ProfileContract.ViewModel {

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

  val mUser: ObservableField<User> = ObservableField()
  val mPageLimit: ObservableField<Int> = ObservableField(PAGE_LIMIT)
  val mIsVisibleButtonFollow: ObservableField<Boolean> = ObservableField()
  val mIsVisibleButtonUnFollow: ObservableField<Boolean> = ObservableField()

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

  override fun onGetUserOtherProfileSuccess(user: User?) {
    mUser.let { it?.set(user) }
  }

  override fun onError(exception: BaseException) {
    Log.e(TAG, exception.getMessageError())
  }

  fun onClickOther() {
    val bundle = Bundle()
    val idUser: Int? = mUser.get().id
    if (idUser != null) {
      bundle.putInt(ProfileActivity.TAG, idUser)
      mNavigator.startActivity(OtherUserActivity::class.java, bundle)
    }
  }

  fun fillData() {
    val idUser: Int? = intent.getIntExtra(BookDetailActivity.TAG, 0)
    if (idUser != Constant.EXTRA_ZERO) {
      //Todo edit later
      mIsVisibleButtonFollow.set(true)
      mIsVisibleButtonUnFollow.set(false)
      mPresenter.getUserOtherProfile(idUser)
      return
    }
    //Todo edit later
    mIsVisibleButtonFollow.set(false)
    mIsVisibleButtonUnFollow.set(false)
    return mUser.let { it?.set(mUserRepository.getUserLocal()) }
  }

  fun onClickArrowBack() {
    mNavigator.finishActivity()
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
