package com.framgia.fbook.screen.profile;

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.databinding.ActivityProfileBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.screen.main.MainActivity
import com.framgia.fbook.screen.sharebook.ShareBookActivity
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.library.MaterialDialog
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
    mUser.let { it?.set(mUserRepository.getUserLocal()) }
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
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

  fun onClickShareBook() {
    mNavigator.startActivity(ShareBookActivity::class.java)
  }

  fun onClickApproveRequest() {
    //Todo navigation Activity Approve Request
  }

  fun onClickSetting() {
    //Todo navigation Activity Setting
  }

  fun onClickLogout() {
    mDialogManager.dialogBasic(getString(R.string.inform),
        getString(R.string.are_u_want_to_sign_out),
        MaterialDialog.SingleButtonCallback { materialDialog, dialogAction ->
          mUserRepository.clearData()
          mNavigator.startActivityAtRoot(MainActivity::class.java)
        })
    return
  }
}
