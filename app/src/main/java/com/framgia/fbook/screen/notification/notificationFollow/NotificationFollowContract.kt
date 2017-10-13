package com.framgia.fbook.screen.notification.notificationFollow

import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface NotificationFollowContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onError(error: BaseException)

    fun onUpdateNotificationSuccess()
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun updateNotification(id: Int?)
  }
}
