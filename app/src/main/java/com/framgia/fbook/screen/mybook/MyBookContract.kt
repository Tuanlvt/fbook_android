package com.framgia.fbook.screen.mybook

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface MyBookContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {

    fun onError(e: BaseException)

    fun onGetMyBookSuccess(listBook: List<Book>?)

    fun onShowProgressDialog()

    fun onDismissProgressDialog()

    fun isNotRefresh(): Boolean
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {

    fun getMyBook(userId: Int?)
  }
}
