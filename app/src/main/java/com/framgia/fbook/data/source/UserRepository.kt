package com.framgia.fbook.data.source

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Follow
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.data.source.remote.api.request.FollowOrUnFollowUserRequest
import com.framgia.fbook.data.source.remote.api.request.UserApproveBookRequest
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.data.source.remote.api.response.NotificationResponse
import com.framgia.fbook.data.source.remote.api.response.TokenResponse
import io.reactivex.Single

/**
 * Created by le.quang.dao on 10/03/2017.
 */

interface UserRepository : UserDataSource.RemoteDataSource, UserDataSource.LocalDataSource

open class UserRepositoryImpl(private val mRemoteDataSource: UserRemoteDataSource,
    private val mLocalDataSource: UserLocalDataSource) : UserRepository {

  override fun getOffices(): Single<BaseResponse<List<Office>>> {
    return mRemoteDataSource.getOffices()
  }

  override fun login(email: String?, password: String?): Single<TokenResponse> {
    return mRemoteDataSource.login(email, password)
  }

  override fun getUser(): Single<BaseResponse<User>> {
    return mRemoteDataSource.getUser()
  }

  override fun saveUser(user: User?) {
    mLocalDataSource.saveUser(user)
  }

  override fun getUserLocal(): User? {
    return mLocalDataSource.getUserLocal()
  }

  override fun clearData() {
    mLocalDataSource.clearData()
  }

  override fun updateUser(): User? {
    return mLocalDataSource.updateUser()
  }

  override fun userApproveBook(bookId: Int?,
      userApproveBookRequest: UserApproveBookRequest?): Single<Any> {
    return mRemoteDataSource.userApproveBook(bookId, userApproveBookRequest)
  }

  override fun getApproveDetail(bookId: Int?): Single<BaseResponse<Book>> {
    return mRemoteDataSource.getApproveDetail(bookId)
  }

  override fun getOtherUserProfile(idUser: Int?): Single<BaseResponse<User>> {
    return mRemoteDataSource.getOtherUserProfile(idUser)
  }

  override fun getFollowInfomationOfUser(userId: Int?): Single<BaseResponse<Follow>> {
    return mRemoteDataSource.getFollowInfomationOfUser(userId)
  }

  override fun followOrUnFollowUser(
      followOrUnFollowUserRequest: FollowOrUnFollowUserRequest?): Single<Any> {
    return mRemoteDataSource.followOrUnFollowUser(followOrUnFollowUserRequest)
  }

  override fun getNotification(): Single<BaseResponse<NotificationResponse>> {
    return mRemoteDataSource.getNotification()
  }
}
