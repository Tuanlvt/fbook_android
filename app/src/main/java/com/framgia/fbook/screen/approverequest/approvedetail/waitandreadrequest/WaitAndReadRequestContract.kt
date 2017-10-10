package com.framgia.fbook.screen.approverequest.approvedetail.waitandreadrequest

import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface WaitAndReadRequestContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onError(e: BaseException)

    fun onShowProgressDialog()

    fun onDismissProgressDialog()

    fun onApproveOrUnApproveBookSuccess()
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun approveOrUnApproveBook(bookId: Int?, userId: Int?, key: String?)
  }
}
