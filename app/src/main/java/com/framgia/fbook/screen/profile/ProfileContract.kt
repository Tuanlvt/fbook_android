package com.framgia.fbook.screen.profile;

import com.framgia.fbook.data.model.BookInUser
import com.framgia.fbook.data.model.Follow
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.FollowOrUnFollowUserRequest
import com.framgia.fbook.screen.BasePresenter
import com.framgia.fbook.screen.BaseViewModel

/**
 * This specifies the contract between the view and the presenter.
 */
interface ProfileContract {
  /**
   * View.
   */
  interface ViewModel : BaseViewModel {
    fun onGetSizeBookInUserProfileSuccess(bookInUser: BookInUser?)

    fun onGetUserOtherProfileSuccess(user: User?)

    fun onGetFollowInfomationOfUserSuccess(follow: Follow?)

    fun onFollowOrUnFollowSuccess()

    fun onShowProgressDialog()

    fun onDismissProgressDialog()

    fun onError(exception: BaseException)
  }

  /**
   * Presenter.
   */
  interface Presenter : BasePresenter<ViewModel> {
    fun getSizeBookInUserProfile(userId: Int?)

    fun getUserOtherProfile(idUser: Int?)

    fun getFollowInfomationOfUser(idUser: Int?)

    fun followOrUnFollow(followOrUnFollowUserRequest: FollowOrUnFollowUserRequest?)
  }
}
