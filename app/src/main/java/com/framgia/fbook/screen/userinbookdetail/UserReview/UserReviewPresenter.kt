package com.framgia.fbook.screen.userinbookdetail.screen.UserReview

import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.ReviewBookRequest
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Listens to user actions from the UI ([UserReviewFragment]), retrieves the data and updates
 * the UI as required.
 */
internal class UserReviewPresenter(
    private val mBookRepository: BookRepository) : UserReviewContract.Presenter {

  private var mViewModel: UserReviewContract.ViewModel? = null

  private lateinit var mSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: UserReviewContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider) {
    mSchedulerProvider = schedulerProvider
  }

  companion object {
    private val TAG = UserReviewPresenter::class.java.name
  }

  override fun reviewBook(bookId: Int?, reviewBookRequest: ReviewBookRequest) {
    val disposable: Disposable = mBookRepository.reviewBook(bookId, reviewBookRequest)
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .doOnSubscribe { mViewModel?.onShowProgressDialog() }
        .doAfterTerminate { mViewModel?.onDismissProgressDialog() }
        .subscribe(
            {
              mViewModel?.onReviewBookSuccess()
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  override fun getBookDetail(bookId: Int?) {
    val disposable: Disposable = mBookRepository.getBookDetail(bookId)
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .doOnSubscribe { mViewModel?.onShowProgressDialog() }
        .doAfterTerminate { mViewModel?.onDismissProgressDialog() }
        .subscribe(
            { book ->
              mViewModel?.onGetBookDetailSuccess(book.item)
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }
}
