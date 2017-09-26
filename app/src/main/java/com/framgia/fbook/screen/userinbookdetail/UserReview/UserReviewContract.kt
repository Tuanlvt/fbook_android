package com.framgia.fbook.screen.userinbookdetail.screen.UserReview

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.ReviewBookRequest
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface UserReviewContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {

    fun onGetBookDetailSuccess(book: Book?)

    fun onReviewBookSuccess()

    fun onError(e: BaseException)

    fun onShowProgressDialog()

    fun onDismissProgressDialog()
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {

    fun reviewBook(bookId: Int?, reviewBookRequest: ReviewBookRequest)

    fun getBookDetail(bookId: Int?)
  }
}
