package com.framia.fbook.screen.main

import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * Created by le.quang.dao on 10/03/2017.
 */

interface MainContract {

  /**
   * ViewModel
   */
  interface ViewModel : BaseViewModel {

    fun onError(baseException: BaseException)

    fun onGetOfficeSuccess(listOffice: List<Office>?)
  }

  /**
   * Presenter
   */
  interface Presenter : BasePresenter<ViewModel> {

    fun getOffices()
  }
}
