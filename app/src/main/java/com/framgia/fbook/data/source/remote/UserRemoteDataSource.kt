package com.framgia.fbook.data.source.remote

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Follow
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserDataSource
import com.framgia.fbook.data.source.remote.api.request.FollowOrUnFollowUserRequest
import com.framgia.fbook.data.source.remote.api.request.SignInRequest
import com.framgia.fbook.data.source.remote.api.request.UserApproveBookRequest
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.data.source.remote.api.response.CountNotification
import com.framgia.fbook.data.source.remote.api.response.NotificationResponse
import com.framgia.fbook.data.source.remote.api.response.TokenResponse
import com.framgia.fbook.data.source.remote.api.service.FBookApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class UserRemoteDataSource @Inject
constructor(nameApi: FBookApi) : BaseRemoteDataSource(nameApi), UserDataSource.RemoteDataSource {

  override fun login(email: String?, password: String?): Single<TokenResponse> {
    val signInRequest = SignInRequest()
    signInRequest.email = email
    signInRequest.password = password
    return fbookApi.login(signInRequest)
  }

  override fun getUser(): Single<BaseResponse<User>> {
    return fbookApi.getUser()
  }

  override fun getOffices(): Single<BaseResponse<List<Office>>> {
    return fbookApi.getOffices()
  }

  override fun userApproveBook(bookId: Int?,
      userApproveBookRequest: UserApproveBookRequest?): Single<Any> {
    return fbookApi.userApproveBook(bookId, userApproveBookRequest)
  }

  override fun getApproveDetail(bookId: Int?): Single<BaseResponse<Book>> {
    return fbookApi.getApproveDetail(bookId)
  }

  override fun getOtherUserProfile(idUser: Int?): Single<BaseResponse<User>> {
    return fbookApi.getOtherUserProfile(idUser)
  }

  override fun getFollowInfomationOfUser(userId: Int?): Single<BaseResponse<Follow>> {
    return fbookApi.getFollowInfomationOfUser(userId)
  }

  override fun followOrUnFollowUser(
      followOrUnFollowUserRequest: FollowOrUnFollowUserRequest?): Single<Any> {
    return fbookApi.followOrUnFollowUser(followOrUnFollowUserRequest)
  }

  override fun getNotification(): Single<BaseResponse<NotificationResponse>> {
    return fbookApi.getNotification()
  }

  override fun getCountNotification(): Single<BaseResponse<CountNotification>> {
    return fbookApi.getCountNotificationUser()
  }

  override fun updateNotification(id: Int?): Single<Any> {
    return fbookApi.updateItemNotification(id)
  }

  override fun readAllNotificationOfUser(): Single<Any> {
    return fbookApi.readAllNotificationOfUser()
  }
}
