package com.framgia.fbook.screen.updatebook;

import com.framgia.fbook.data.source.remote.api.request.BookRequest
import com.framgia.fbook.screen.BasePresenter;
import com.framgia.fbook.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface UpdateBookContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onInputTitleError(errorMsg: String?)

    fun onInputAuthorError(errorMsg: String?)

    fun onInputDescriptionError(errorMsg: String?)
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun validateDataInput(bookRequest: BookRequest): Boolean

    fun validateTitleInput(title: String?)

    fun validatorAuthor(author: String?)

    fun validatorDescription(description: String?)
  }
}
