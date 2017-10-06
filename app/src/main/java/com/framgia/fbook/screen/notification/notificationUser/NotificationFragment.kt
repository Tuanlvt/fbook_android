package com.framgia.fbook.screen.notification.notificationUser

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.response.NotificationResponse
import com.framgia.fbook.databinding.FragmentNotificationBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.main.MainActivity
import com.framgia.fbook.screen.notification.NotificationAdapter
import com.framgia.fbook.screen.onItemRecyclerViewClickListener
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * Notification Screen.
 */
class NotificationFragment : BaseFragment(), NotificationContract.ViewModel, onItemRecyclerViewClickListener {

  @Inject
  internal lateinit var mPresenter: NotificationContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mNotificationAdapter: NotificationAdapter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerNotificationComponent.builder()
        .mainComponent((activity as MainActivity).getMainComponent())
        .notificationModule(NotificationModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentNotificationBinding>(inflater,
        R.layout.fragment_notification, container,
        false)
    binding.viewModel = this
    mPresenter.getNotification()
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

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onError(error: BaseException) {
    mDialogManager.dialogError(error.getMessageError())
  }

  override fun onItemClickListener(any: Any?) {
    //TODO edit later
  }

  override fun onGetNotificationSuccess(notificationResponse: NotificationResponse?) {
    notificationResponse?.let {
      notificationResponse.notificationUser?.let {
        mNotificationAdapter.updateData(it.listNotification)
      }
    }
  }

  companion object {

    val TAG: String = NotificationFragment::class.java.name

    fun newInstance(): NotificationFragment {
      return NotificationFragment()
    }
  }
}
