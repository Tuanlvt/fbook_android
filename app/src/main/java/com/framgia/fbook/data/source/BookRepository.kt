package com.framgia.fbook.data.source

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.remote.BookRemoteDataSource
import com.framgia.fbook.data.source.remote.api.request.SearchBookRequest
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
  override fun searchBook(searchBookRequest: SearchBookRequest): Single<BaseResponse<BaseBookRespone<List<Book>>>> {
    return bookRemoteDataSource.searchBook(searchBookRequest)
  }

}
