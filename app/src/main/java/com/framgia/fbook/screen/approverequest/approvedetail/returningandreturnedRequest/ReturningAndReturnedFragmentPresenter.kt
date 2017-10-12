package com.framgia.fbook.screen.approverequest.approvedetail.returningandreturnedRequest

import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.UserApproveBookRequest
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Listens to user actions from the UI ([ReturningAndReturnedFragmentFragment]), retrieves the
 * data and updates
 * the UI as required.
 */
open class ReturningAndReturnedFragmentPresenter(
    private val mUserRepository: UserRepository) : ReturningAndReturnedFragmentContract.Presenter {

  private lateinit var mViewModel: ReturningAndReturnedFragmentContract.ViewModel
  private lateinit var mSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun returningBook(bookId: Int?, userId: Int?, key: String?) {
    val userApproveBookRequest = UserApproveBookRequest()
    userApproveBookRequest.data?.userId = userId
    userApproveBookRequest.data?.key = key
    val disposable = mUserRepository.userApproveBook(bookId, userApproveBookRequest)
        .subscribeOn(mSchedulerProvider.io())
        .doOnSubscribe { mViewModel.onShowProgressDialog() }
        .doAfterTerminate { mViewModel.onDismissProgressDialog() }
        .observeOn(mSchedulerProvider.ui())
        .subscribe({ mViewModel.onReturnedBookSuccess() },
            { error -> mViewModel.onError(error as BaseException) })
    mCompositeDisposable.add(disposable)
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
