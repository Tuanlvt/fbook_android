package com.framgia.fbook.screen.profile;

import com.framgia.fbook.data.model.Follow
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BasePresenter;
import com.framgia.fbook.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ProfileContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {

    fun onGetUserOtherProfileSuccess(user: User?)

    fun onGetFollowInfomationOfUserSuccess(follow: Follow?)

    fun onError(exception: BaseException)
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {

    fun getUserOtherProfile(idUser: Int?)

    fun getFollowInfomationOfUser(idUser: Int?)
  }
}
