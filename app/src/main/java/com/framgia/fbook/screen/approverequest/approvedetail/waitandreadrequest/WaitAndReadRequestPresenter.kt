package com.framgia.fbook.screen.approverequest.approvedetail.waitandreadrequest

import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Listens to user actions from the UI ([WaitAndReadRequestFragment]), retrieves the data and
 * updates
 * the UI as required.
 */
open class WaitAndReadRequestPresenter : WaitAndReadRequestContract.Presenter {

  private lateinit var mViewModel: WaitAndReadRequestContract.ViewModel
  private lateinit var mSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: WaitAndReadRequestContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider) {
    mSchedulerProvider = schedulerProvider
  }

  companion object {
    private val TAG = WaitAndReadRequestPresenter::class.java.name
  }
}
