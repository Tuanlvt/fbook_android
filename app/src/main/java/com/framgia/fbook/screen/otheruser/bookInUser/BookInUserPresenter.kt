package com.framgia.fbook.screen.otheruser.bookInUser

import com.framgia.fbook.data.model.ActionBookDetail
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Listens to user actions from the UI ([BookInUserFragment]), retrieves the data and updates
 * the UI as required.
 */
open class BookInUserPresenter(
    private val bookRepository: BookRepository) : BookInUserContract.Presenter {

  private var mViewModel: BookInUserContract.ViewModel? = null
  private lateinit var mBaseSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }


  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: BookInUserContract.ViewModel) {
    mViewModel = viewModel
  }

  override fun getBookInUserProfile(userId: Int?, type: String?) {
    val disposable: Disposable = bookRepository.getFeatureOtherOfUser(userId, type)
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .subscribe({ book ->
          book.items?.data?.let { mViewModel?.onGetBookInUserProfileSuccess(it) }
        }, { error
          ->
          mViewModel?.onError(error as BaseException)
        })
    mCompositeDisposable.add(disposable)
  }

  override fun returnBook(actionBookDetail: ActionBookDetail?) {
    val disposable: Disposable = bookRepository.returnBook(actionBookDetail)
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .subscribe({
          mViewModel?.onReturnBookSuccess()
        }, {
          error ->
          mViewModel?.onError(error as BaseException)
        })
    mCompositeDisposable.add(disposable)
  }

  fun setSchedulerProvider(baseSchedulerProvider: BaseSchedulerProvider) {
    mBaseSchedulerProvider = baseSchedulerProvider
  }

  companion object {
    private val TAG = BookInUserPresenter::class.java.name
  }
}
