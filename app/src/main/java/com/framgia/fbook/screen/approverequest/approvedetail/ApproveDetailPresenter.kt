package com.framgia.fbook.screen.approverequest.approvedetail;

import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Listens to user actions from the UI ({@link ApproveDetailActivity}), retrieves the data and updates
 * the UI as required.
 */
class ApproveDetailPresenter(
    private val mUserRepository: UserRepository) : ApproveDetailContract.Presenter {

  private lateinit var mViewModel: ApproveDetailContract.ViewModel
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
  private lateinit var mBaseSchedulerProvider: BaseSchedulerProvider

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun getApproveDetail(bookId: Int?) {
    val disposable: Disposable = mUserRepository.getApproveDetail(bookId)
        .subscribeOn(mBaseSchedulerProvider.io())
        .doOnSubscribe { mViewModel.onShowProgressDialog() }
        .doAfterTerminate { mViewModel.onDismissProgressDialog() }
        .observeOn(mBaseSchedulerProvider.ui())
        .subscribe(
            { bookResponse ->
              mViewModel.onGetApproveDetailSuccess(bookResponse.item)
            },
            { error ->
              mViewModel.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  override fun setViewModel(viewModel: ApproveDetailContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(baseSchedulerProvider: BaseSchedulerProvider) {
    mBaseSchedulerProvider = baseSchedulerProvider
  }
}
