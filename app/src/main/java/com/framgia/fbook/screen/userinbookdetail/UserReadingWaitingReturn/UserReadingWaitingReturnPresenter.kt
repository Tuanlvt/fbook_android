package com.framgia.fbook.screen.userinbookdetail.screen.UserReadingWaitingReturn

import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Listens to user actions from the UI ([UserReadingWaitingReturnFragment]), retrieves the
 * data and updates
 * the UI as required.
 */
internal class UserReadingWaitingReturnPresenter : UserReadingWaitingReturnContract.Presenter {

  private var mViewModel: UserReadingWaitingReturnContract.ViewModel? = null

  private lateinit var mSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: UserReadingWaitingReturnContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider) {
    mSchedulerProvider = schedulerProvider
  }
  
  companion object {
    private val TAG = UserReadingWaitingReturnPresenter::class.java.name
  }
}
