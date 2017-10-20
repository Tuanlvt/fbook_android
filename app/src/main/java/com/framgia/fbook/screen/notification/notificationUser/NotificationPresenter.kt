package com.framgia.fbook.screen.notification.notificationUser

import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Listens to user actions from the UI ([NotificationFragment]), retrieves the data and
 * updates
 * the UI as required.
 */
class NotificationPresenter(
    private val mUserRepository: UserRepository) : NotificationContract.Presenter {

  private lateinit var mViewModel: NotificationContract.ViewModel
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
        }, { error ->
          mViewModel.onError(error as BaseException)
        })
    mCompositeDisposable.add(disposable)
  }

  override fun getNotification() {
    val disposable = mUserRepository.getNotification()
        .subscribeOn(mSchedulerProvider.io())
        .doOnSubscribe {
          if (!mViewModel.onIsNotRefresh()) {
            mViewModel.onShowProgressDialog()
          }
        }
        .doAfterSuccess { mViewModel.onDismissProgressDialog() }
        .observeOn(mSchedulerProvider.ui())
        .subscribe({ notificationResponse ->
          mViewModel.onGetNotificationSuccess(notificationResponse.items)
        }, { error ->
          mViewModel.onError(error as BaseException)
        })
    mCompositeDisposable.add(disposable)
  }

  override fun readAllNotificationOfUser() {
    val mDisable = mUserRepository.readAllNotificationOfUser()
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe({
          mViewModel.onReadAllNotificationSuccess()
        }, { error -> mViewModel.onError(error as BaseException) })
    mCompositeDisposable.addAll(mDisable)
  }

  override fun setViewModel(viewModel: NotificationContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider) {
    mSchedulerProvider = schedulerProvider
  }

  companion object {
    private val TAG = NotificationPresenter::class.java.name
  }
}
