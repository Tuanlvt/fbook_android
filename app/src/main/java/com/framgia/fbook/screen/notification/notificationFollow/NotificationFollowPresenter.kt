package com.framgia.fbook.screen.notification.notificationFollow

/**
 * Listens to user actions from the UI ([NotificationFollowFragment]), retrieves the data and
 * updates
 * the UI as required.
 */
open class NotificationFollowPresenter : NotificationFollowContract.Presenter {

  private var mViewModel: NotificationFollowContract.ViewModel? = null

  override fun onStart() {}

  override fun onStop() {}

  override fun setViewModel(viewModel: NotificationFollowContract.ViewModel) {
    mViewModel = viewModel
  }

  companion object {
    private val TAG = NotificationFollowPresenter::class.java.name
  }
}
