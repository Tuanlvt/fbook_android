package com.framgia.fbook.screen.approverequest.approvedetail;

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface ApproveDetailContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onError(e: BaseException)

    fun onGetApproveDetailSuccess(book: Book?)

    fun onShowProgressDialog()

    fun onDismissProgressDialog()
  }


  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun getApproveDetail(bookId: Int?)
  }

}
