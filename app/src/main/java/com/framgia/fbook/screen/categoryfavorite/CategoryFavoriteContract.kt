package com.framgia.fbook.screen.categoryfavorite

import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
internal interface CategoryFavoriteContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onGetUserSuccess(user: User?)

    fun onError(exception: BaseException)
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun getUser()
  }
}
