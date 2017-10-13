package com.framgia.fbook.screen.notification.notificationFollow

import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Listens to user actions from the UI ([NotificationFollowFragment]), retrieves the data and
 * updates
 * the UI as required.
 */
open class NotificationFollowPresenter(
    private val mUserRepository: UserRepository) : NotificationFollowContract.Presenter {

  private lateinit var mViewModel: NotificationFollowContract.ViewModel
  private lateinit var mSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun updateNotification(id: Int?) {
    val disposable = mUserRepository.updateNotification(id)
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe({
          mViewModel.onUpdateNotificationSuccess()
        }, {
          error ->
          mViewModel.onError(error as BaseException)
        })
    mCompositeDisposable.add(disposable)
  }

  override fun setViewModel(viewModel: NotificationFollowContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider) {
    mSchedulerProvider = schedulerProvider
  }

  companion object {
    private val TAG = NotificationFollowPresenter::class.java.name
  }
}
