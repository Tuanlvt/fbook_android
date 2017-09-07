package com.framgia.fbook.screen.sharebook;

import com.framgia.fbook.screen.BasePresenter;
import com.framgia.fbook.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ShareBookContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
  }
}