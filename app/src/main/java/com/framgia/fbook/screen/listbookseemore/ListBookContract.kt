package com.framgia.fbook.screen.listbookseemore

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.Sort
import com.framgia.fbook.data.model.SortBook
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface ListBookContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onError(exception: BaseException)

    fun onGetListBookSuccess(listBook: List<Book>?)

    fun onShowProgressBarDialog()

    fun onDismissProgressBarDialog()

    fun onGetListCategorySuccess(listCategory: List<Category>?)

    fun onGetListSortBookSuccess(listSort: List<SortBook>?)

    fun isShowProgressDialog(): Boolean
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun getListBook(typeBook: String?, page: Int?, officeId: Int?)

    fun getListCategory()

    fun getListBookByCategory(categoryId: Int?, officeId: Int?)

    fun getListSortBook()

    fun getListBookBySort(type: String?, page: Int?, sort: Sort?, officeId: Int?)
  }
}
