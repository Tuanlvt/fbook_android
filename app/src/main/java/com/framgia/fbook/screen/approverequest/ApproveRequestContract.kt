package com.framgia.fbook.screen.approverequest

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.UserApproveBookRequest
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface ApproveRequestContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onError(e: BaseException)

    fun onGetApproveRequestSuccess(listBook: List<Book>?)

    fun onApproveBookSuccess()

    fun onShowProgressDialog()

    fun onDismissProgressDialog()
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun getApproveRequest()

    fun approveBook(bookId: Int?, userApproveBookRequest: UserApproveBookRequest?)
  }
}
