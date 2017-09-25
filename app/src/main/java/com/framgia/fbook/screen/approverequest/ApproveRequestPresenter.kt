package com.framgia.fbook.screen.approverequest;

import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Listens to user actions from the UI ({@link ApproveRequestActivity}), retrieves the data and updates
 * the UI as required.
 */
class ApproveRequestPresenter(
    private val mBookRepository: BookRepository) : ApproveRequestContract.Presenter {
  private var mViewModel: ApproveRequestContract.ViewModel? = null
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
  private lateinit var mBaseSchedulerProvider: BaseSchedulerProvider
  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: ApproveRequestContract.ViewModel) {
    mViewModel = viewModel
  }

  override fun getApproveRequest() {
    val disposable: Disposable = mBookRepository.getApproveRequest()
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .doOnSubscribe { mViewModel?.onShowProgressDialog() }
        .doAfterTerminate { mViewModel?.onDismissProgressDialog() }
        .subscribe(
            { listBook ->
              mViewModel?.onGetApproveRequestSuccess(listBook.items?.data)
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  fun setSchedulerProvider(baseSchedulerProvider: BaseSchedulerProvider) {
    mBaseSchedulerProvider = baseSchedulerProvider
  }
}
