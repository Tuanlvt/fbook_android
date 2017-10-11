package com.framgia.fbook.data.source

import com.framgia.fbook.data.model.*
import com.framgia.fbook.data.source.remote.BookRemoteDataSource
import com.framgia.fbook.data.source.remote.api.request.BookRequest
import com.framgia.fbook.data.source.remote.api.request.ReviewBookRequest
import com.framgia.fbook.data.source.remote.api.response.BaseBookRespone
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Hyperion on 9/5/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
interface BookRepository : BookDataSource.BookRemoteDataSource

class BookRepositoryImpl @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource) : BookRepository {

  override fun getApproveRequest(): Single<BaseResponse<BaseBookRespone<List<Book>>>> {
    return bookRemoteDataSource.getApproveRequest()
  }

  override fun getMyBook(userId: Int?): Single<BaseResponse<BaseBookRespone<List<Book>>>> {
    return bookRemoteDataSource.getMyBook(userId)
  }

  override fun searchBook(
      keyword: String?, field: String?): Single<BaseResponse<BaseBookRespone<List<Book>>>> {
    return bookRemoteDataSource.searchBook(keyword, field)
  }

  override fun searchBookWithGoogleApi(
      bookName: String?): Single<BaseResponse<List<GoogleBook>>> {
    return bookRemoteDataSource.searchBookWithGoogleApi(bookName)
  }

  override fun getSectionListBook(type: String?,
      page: Int?, officeId: Int?): Single<BaseResponse<BaseBookRespone<List<Book>>>> {
    return bookRemoteDataSource.getSectionListBook(type, page, officeId)
  }

  override fun getBookDetail(bookId: Int?): Single<BaseResponse<Book>> {
    return bookRemoteDataSource.getBookDetail(bookId)
  }

  override fun addBook(bookRequest: BookRequest): Single<BaseResponse<Book>> {
    return bookRemoteDataSource.addBook(bookRequest)
  }

  override fun addUserHaveThisBook(bookId: Int?): Single<Any> {
    return bookRemoteDataSource.addUserHaveThisBook(bookId)
  }

  override fun removeOwnerThisBook(bookId: Int?): Single<Any> {
    return bookRemoteDataSource.removeOwnerThisBook(bookId)
  }

  override fun readOrCancelBook(actionBookDetail: ActionBookDetail?): Single<Any> {
    return bookRemoteDataSource.readOrCancelBook(actionBookDetail)
  }

  override fun getListSortBook(): Single<BaseResponse<List<SortBook>>> {
    return bookRemoteDataSource.getListSortBook()
  }

  override fun getListBookBySort(type: String?,
      page: Int?, sort: Sort?, officeId: Int?): Single<BaseResponse<BaseBookRespone<List<Book>>>> {
    return bookRemoteDataSource.getListBookBySort(type, page, sort, officeId)
  }

  override fun reviewBook(bookId: Int?, reviewBookRequest: ReviewBookRequest?): Single<Any> {
    return bookRemoteDataSource.reviewBook(bookId, reviewBookRequest)
  }

  override fun getFeatureOtherOfUser(userId: Int?,
      type: String?): Single<BaseResponse<BaseBookRespone<List<Book>>>> {
    return bookRemoteDataSource.getFeatureOtherOfUser(userId, type)
  }

  override fun returnBook(actionBookDetail: ActionBookDetail?): Single<Any> {
    return bookRemoteDataSource.returnBook(actionBookDetail)
  }
}
