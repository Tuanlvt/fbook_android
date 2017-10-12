package com.framgia.fbook.screen.approverequest.approvedetail.returningandreturnedRequest

import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface ReturningAndReturnedFragmentContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onError(e: BaseException)

    fun onShowProgressDialog()

    fun onDismissProgressDialog()

    fun onReturnedBookSuccess()
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun returningBook(bookId: Int?, userId: Int?, key: String?)
  }
}
