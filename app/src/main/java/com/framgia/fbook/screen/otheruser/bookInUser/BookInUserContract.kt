package com.framgia.fbook.screen.otheruser.bookInUser

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface BookInUserContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onGetBookInUserProfileSuccess(book: List<Book>)

    fun onError(exception: BaseException)
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun getBookInUserProfile(userId: Int?, type: String?)
  }
}
