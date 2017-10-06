package com.framgia.fbook.screen.notification

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.databinding.FragmentNotificationcontainerBinding
import com.framgia.fbook.screen.BaseFragment

/**
 * NotificationContainer Screen.
 */
class NotificationContainerFragment : BaseFragment() {

  private val mNotificationContainerAdapter: NotificationContainerAdapter by lazy {
    NotificationContainerAdapter(context, fragmentManager)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    val binding = DataBindingUtil.inflate<FragmentNotificationcontainerBinding>(inflater,
        R.layout.fragment_notificationcontainer,
        container, false)
    binding.viewModel = this
    return binding.root
  }

  override fun onStart() {
    super.onStart()
  }

  override fun onStop() {
    super.onStop()
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
