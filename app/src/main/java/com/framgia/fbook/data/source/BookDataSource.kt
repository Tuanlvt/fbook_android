package com.framgia.fbook.data.source

import com.framgia.fbook.data.model.*
import com.framgia.fbook.data.source.remote.api.request.BookRequest
import com.framgia.fbook.data.source.remote.api.request.ReviewBookRequest
import com.framgia.fbook.data.source.remote.api.response.BaseBookRespone
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import io.reactivex.Single

/**
 * Created by Hyperion on 9/5/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
interface BookDataSource {
  /**
   * RemoteData For Book
   */
  interface BookRemoteDataSource {
    fun searchBook(
        keyword: String?, field: String?): Single<BaseResponse<BaseBookRespone<List<Book>>>>

    fun getMyBook(userId: Int?): Single<BaseResponse<BaseBookRespone<List<Book>>>>

    fun searchBookWithGoogleApi(
        bookName: String?): Single<BaseResponse<List<GoogleBook>>>

    fun getSectionListBook(type: String?,
        page: Int?): Single<BaseResponse<BaseBookRespone<List<Book>>>>

    fun getBookDetail(bookId: Int?): Single<BaseResponse<Book>>

    fun addBook(bookRequest: BookRequest): Single<BaseResponse<Book>>

    fun addUserHaveThisBook(bookId: Int?): Single<Any>

    fun removeOwnerThisBook(bookId: Int?): Single<Any>

    fun readOrCancelBook(actionBookDetail: ActionBookDetail?): Single<Any>

    fun getListSortBook(): Single<BaseResponse<List<SortBook>>>

    fun getApproveRequest(): Single<BaseResponse<BaseBookRespone<List<Book>>>>

    fun getListBookBySort(type: String?,
        page: Int?, sort: Sort?): Single<BaseResponse<BaseBookRespone<List<Book>>>>

    fun reviewBook(bookId: Int?, reviewBookRequest: ReviewBookRequest?): Single<Any>
  }
}
