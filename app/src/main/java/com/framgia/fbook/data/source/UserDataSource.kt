package com.framgia.fbook.data.source

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Follow
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.remote.api.request.FollowOrUnFollowUserRequest
import com.framgia.fbook.data.source.remote.api.request.UserApproveBookRequest
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.data.source.remote.api.response.CountNotification
import com.framgia.fbook.data.source.remote.api.response.NotificationResponse
import com.framgia.fbook.data.source.remote.api.response.TokenResponse
import io.reactivex.Single

/**
 * Created by le.quang.dao on 10/03/2017.
 */

interface UserDataSource {
  /**
   * LocalData For User
   */
  interface LocalDataSource {
    fun saveUser(user: User?)

    fun getUserLocal(): User?

    fun clearData()

    fun updateUser(): User?

  }

  /**
   * RemoteData For User
   */
  interface RemoteDataSource {
    fun login(email: String?, password: String?): Single<TokenResponse>

    fun getUser(): Single<BaseResponse<User>>

    fun getOffices(): Single<BaseResponse<List<Office>>>

    fun userApproveBook(bookId: Int?, userApproveBookRequest: UserApproveBookRequest?): Single<Any>

    fun getOtherUserProfile(idUser: Int?): Single<BaseResponse<User>>

    fun getApproveDetail(bookId: Int?): Single<BaseResponse<Book>>

    fun getFollowInfomationOfUser(userId: Int?): Single<BaseResponse<Follow>>

    fun followOrUnFollowUser(followOrUnFollowUserRequest: FollowOrUnFollowUserRequest?): Single<Any>

    fun getNotification(): Single<BaseResponse<NotificationResponse>>

    fun getCountNotification(): Single<BaseResponse<CountNotification>>

    fun updateNotification(id: Int?): Single<Any>

    fun readAllNotificationOfUser(): Single<Any>
  }
}
