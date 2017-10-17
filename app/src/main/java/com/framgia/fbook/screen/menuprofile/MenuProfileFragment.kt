package com.framgia.fbook.screen.menuprofile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.databinding.FragmentMenuProfileBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.approverequest.ApproveRequestActivity
import com.framgia.fbook.screen.login.LoginActivity
import com.framgia.fbook.screen.main.MainActivity
import com.framgia.fbook.screen.main.NotificationListener
import com.framgia.fbook.screen.profile.ProfileActivity
import com.framgia.fbook.screen.sharebook.ShareBookActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * Menuprofile Screen.
 */
class MenuProfileFragment : BaseFragment(), MenuProfileContract.ViewModel {

  private val mNavigator: Navigator by lazy { Navigator(this) }
  @Inject
  internal lateinit var mPresenter: MenuProfileContract.Presenter
  @Inject
  internal lateinit var mUserRepository: UserRepository
  @Inject
  internal lateinit var mDialogManager: DialogManager
  private lateinit var mMenuProfileComponent: MenuProfileComponent
  private lateinit var mNotificationLoginListener: NotificationListener.LoginListener
  private lateinit var mLayoutView: View
  private var mIsLoadDataFirstTime: Boolean = true
  val mUser: ObservableField<User> = ObservableField()
  val mIsVisibleLayoutNotLoggedIn: ObservableField<Boolean> = ObservableField()
  val mPageLimit: ObservableField<Int> = ObservableField(PAGE_LIMIT)

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    DaggerMenuProfileComponent.builder()
        .mainComponent((activity as MainActivity).getMainComponent())
        .menuProfileModule(MenuProfileModule(this))
        .build()
        .inject(this)
    val binding = DataBindingUtil.inflate<FragmentMenuProfileBinding>(inflater,
        R.layout.fragment_menu_profile, container, false)
    binding.viewModel = this
    mLayoutView = binding.frameLayoutImage
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
      mNotificationLoginListener = context
    }
  }

  companion object {
    private val PAGE_LIMIT = 1
    val TAG: String = "MenuProfileFragment"
    fun newInstance(): MenuProfileFragment {
      return MenuProfileFragment()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK && requestCode == Constant.RequestCode.TAB_PROFILE_REQUEST) {
      mIsVisibleLayoutNotLoggedIn.set(true)
      mNotificationLoginListener.IsLoggedIn(true)
      mUser.set(mUserRepository.getUserLocal())
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
                  Constant.RequestCode.TAB_PROFILE_REQUEST)
            })
        mIsVisibleLayoutNotLoggedIn.set(false)
        return
      }
      mIsVisibleLayoutNotLoggedIn.set(true)
      mUser.set(user)
    }
  }

  fun onClickProfile() {
    mNavigator.startActivity(ProfileActivity::class.java)
  }

  fun onClickShareBook() {
    mNavigator.startActivity(ShareBookActivity::class.java)
  }

  fun onClickApproveRequest() {
    mNavigator.startActivity(ApproveRequestActivity::class.java)
  }

  fun onClickSetting() {
    mDialogManager.showSnackBarNoActionBar(mLayoutView, R.string.this_feature_will_be_update_soon)
  }

  fun onClickSignOut() {
    mDialogManager.dialogBasic(getString(R.string.inform),
        getString(R.string.are_u_want_to_sign_out),
        MaterialDialog.SingleButtonCallback { _, _ ->
          mUserRepository.clearData()
          mNavigator.startActivityAtRoot(MainActivity::class.java)
        })
    return
  }

  override fun getUserVisibleHint(): Boolean {
    return super.getUserVisibleHint()
  }
}
