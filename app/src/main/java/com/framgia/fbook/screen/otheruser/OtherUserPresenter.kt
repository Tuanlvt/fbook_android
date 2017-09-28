package com.framgia.fbook.screen.otheruser;

import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Listens to user actions from the UI ({@link OtherUserActivity}), retrieves the data and updates
 * the UI as required.
 */
class OtherUserPresenter : OtherUserContract.Presenter {

  private var mViewModel: OtherUserContract.ViewModel? = null

  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
  private lateinit var mBaseSchedulerProvider: BaseSchedulerProvider

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: OtherUserContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(baseSchedulerProvider: BaseSchedulerProvider) {
    mBaseSchedulerProvider = baseSchedulerProvider
  }
}
