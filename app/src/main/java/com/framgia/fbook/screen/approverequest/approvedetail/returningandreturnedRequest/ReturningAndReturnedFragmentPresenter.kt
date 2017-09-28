package com.framgia.fbook.screen.approverequest.approvedetail.returningandreturnedRequest

import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Listens to user actions from the UI ([ReturningAndReturnedFragmentFragment]), retrieves the
 * data and updates
 * the UI as required.
 */
open class ReturningAndReturnedFragmentPresenter : ReturningAndReturnedFragmentContract.Presenter {

  private lateinit var mViewModel: ReturningAndReturnedFragmentContract.ViewModel
  private lateinit var mSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: ReturningAndReturnedFragmentContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider) {
    mSchedulerProvider = schedulerProvider
  }

  companion object {
    private val TAG = ReturningAndReturnedFragmentPresenter::class.java.name
  }
}
