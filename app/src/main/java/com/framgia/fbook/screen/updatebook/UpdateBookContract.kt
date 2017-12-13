package com.framgia.fbook.screen.updatebook;

import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.EditBookRequest
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

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

    fun onError(exception: BaseException)

    fun onGetOfficeSuccess(listOffice: List<Office>?)

    fun onGetCategorySuccess(listCategory: List<Category>?)

    fun onRequestFormEditBookSuccess()

    fun onShowProgressDialog()

    fun onHideProgressDialog()
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun validateDataInput(editBookRequest: EditBookRequest): Boolean

    fun validateTitleInput(title: String?)

    fun validatorAuthor(author: String?)

    fun validatorDescription(description: String?)

    fun getData()

    fun sendFormEditBook(bookId: Int?, editBookRequest: EditBookRequest)
  }
}
