package com.framgia.fbook.screen.notification

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
import com.framgia.fbook.data.source.local.sharedprf.SharedPrefsApi
import com.framgia.fbook.data.source.local.sharedprf.SharedPrefsImpl
import com.framgia.fbook.data.source.local.sharedprf.SharedPrefsKey
import com.framgia.fbook.databinding.FragmentNotificationcontainerBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.login.LoginActivity
import com.framgia.fbook.screen.main.MainActivity
import com.framgia.fbook.screen.main.NotificationListener
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.common.StringUtils
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import com.google.gson.Gson

/**
 * NotificationContainer Screen.
 */
class NotificationContainerFragment : BaseFragment() {

  private val mNotificationContainerAdapter: NotificationContainerAdapter by lazy {
    NotificationContainerAdapter(context, fragmentManager)
  }
  private val mSharedPrefsApi: SharedPrefsApi by lazy { SharedPrefsImpl(context) }
  private val mDialogManager: DialogManager by lazy { DialogManagerImpl(context) }
  private val mNavigator: Navigator by lazy { Navigator(this) }
  private lateinit var mNotificationListener: NotificationListener.UserListener
  val mIsVisibleLayoutNotLoggedIn: ObservableField<Boolean> = ObservableField()
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    val binding = DataBindingUtil.inflate<FragmentNotificationcontainerBinding>(inflater,
        R.layout.fragment_notificationcontainer,
        container, false)
    binding.viewModel = this
    return binding.root
  }


  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is MainActivity) {
      mNotificationListener = context
    }
  }

  private fun getUserLocal(): User? {
    val data = mSharedPrefsApi[SharedPrefsKey.KEY_USER, String::class.java]
    return if (StringUtils.isBlank(data)) {
      null
    } else {
      Gson().fromJson(data, User::class.java)
    }
  }

  private fun showDialogLogin() {
    mDialogManager.dialogBasic(getString(R.string.inform),
        getString(R.string.you_must_be_login_into_perform_this_function),
        MaterialDialog.SingleButtonCallback { _, _ ->
          mNavigator.startActivityForResultFromFragment(LoginActivity::class.java,
              Constant.RequestCode.TAB_NOTIFICATION)
        })
  }

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (!isVisibleToUser) {
      return
    }
    val user = getUserLocal()
    if (user == null) {
      showDialogLogin()
      mIsVisibleLayoutNotLoggedIn.set(true)
      return
    }
    mNotificationListener.getEnable(true)
    mIsVisibleLayoutNotLoggedIn.set(false)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK && requestCode == Constant.RequestCode.TAB_NOTIFICATION) {
      mIsVisibleLayoutNotLoggedIn.set(false)
      mNotificationListener.getEnable(true)
    }
  }
  fun getNotificationContainerAdapter(): NotificationContainerAdapter {
    return mNotificationContainerAdapter
  }

  companion object {

    fun newInstance(): NotificationContainerFragment {
      return NotificationContainerFragment()
    }
  }
}
