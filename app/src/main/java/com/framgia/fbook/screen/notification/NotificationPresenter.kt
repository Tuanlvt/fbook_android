package com.framgia.fbook.screen.notification

import com.framgia.fbook.data.source.UserRepository
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
