package com.framgia.fbook.screen.notification.notificationUser

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.response.NotificationResponse
import com.framgia.fbook.databinding.FragmentNotificationBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.main.MainActivity
import com.framgia.fbook.screen.main.NotificationListener
import com.framgia.fbook.screen.notification.NotificationAdapter
import com.framgia.fbook.screen.onItemRecyclerViewClickListener
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * Notification Screen.
 */
class NotificationFragment : BaseFragment(), NotificationContract.ViewModel, onItemRecyclerViewClickListener, NotificationUserListener {

  @Inject
  internal lateinit var mPresenter: NotificationContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mNotificationAdapter: NotificationAdapter
  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mUserRepository: UserRepository
  private lateinit var mNotificationListener: NotificationListener
  private var mIsLoadDataFirstTime = true
  val mIsVisibleLayoutNotData: ObservableField<Boolean> = ObservableField()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    userVisibleHint = false
    DaggerNotificationComponent.builder()
        .mainComponent((activity as MainActivity).getMainComponent())
        .notificationModule(NotificationModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentNotificationBinding>(inflater,
        R.layout.fragment_notification, container,
        false)
    binding.viewModel = this
    mNotificationAdapter.setonItemRecyclerViewClickListener(this)
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
      mNotificationListener = context
      context.setNotificationUserListener(this)
    }
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onError(error: BaseException) {
    mIsLoadDataFirstTime = false
    mDialogManager.dialogError(error.getMessageError())
  }

  override fun onItemClickListener(any: Any?) {
    //TODO edit later
  }

  override fun getEnable(enable: Boolean) {
    if (enable && mIsLoadDataFirstTime) {
      mPresenter.getNotification()
    }
  }

  override fun onGetNotificationSuccess(notificationResponse: NotificationResponse?) {
    mIsLoadDataFirstTime = false
    notificationResponse?.let {
      notificationResponse.notificationUser?.let {
        mNotificationAdapter.updateData(it.listNotification, TYPE_USER)
      }
      notificationResponse.notificationFollow?.let {
        mNotificationListener.getNotificationFollow(it)
      }
    }
  }

  companion object {

    private val TYPE_USER = 0
    val TAG: String = NotificationFragment::class.java.name

    fun newInstance(): NotificationFragment {
      return NotificationFragment()
    }
  }
}
