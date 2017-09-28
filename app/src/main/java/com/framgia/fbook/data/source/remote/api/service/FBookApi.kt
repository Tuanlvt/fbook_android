package com.framgia.fbook.data.source.remote.api.service

import com.framgia.fbook.data.model.*
import com.framgia.fbook.data.source.remote.api.request.*
import com.framgia.fbook.data.source.remote.api.response.BaseBookByCategoryResponse
import com.framgia.fbook.data.source.remote.api.response.BaseBookRespone
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.data.source.remote.api.response.TokenResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by le.quang.dao on 10/03/2017.
 */

interface FBookApi {
  @GET("/api/v0/home/")
  fun getHome(@Query("office_id") officeId: Int?): Single<BaseResponse<List<BookType>>>

  @POST("/api/v0/login")
  fun login(@Body signInRequest: SignInRequest?): Single<TokenResponse>

  @POST("/api/v0/search")
  fun searchBook(
      @Body searchBookRequest: SearchBookRequest): Single<BaseResponse<BaseBookRespone<List<Book>>>>

  @GET("api/v0/search-books")
  fun searchBookWithGoogleApi(
      @QueryMap map: Map<String, String?>): Single<BaseResponse<List<GoogleBook>>>

  @GET("/api/v0/user-profile")
  fun getUser(): Single<BaseResponse<User>>

  @GET("/api/v0/users/book/{user_id}/sharing")
  fun getMyBook(@Path("user_id") userId: Int?): Single<BaseResponse<BaseBookRespone<List<Book>>>>

  @GET("/api/v0/offices")
  fun getOffices(): Single<BaseResponse<List<Office>>>

  @GET("/api/v0/books/")
  fun getSectionListBook(@Query("field") type: String?, @Query(
      "page") page: Int?): Single<BaseResponse<BaseBookRespone<List<Book>>>>

  @GET("/api/v0/categories")
  fun getCategory(): Single<BaseResponse<List<Category>>>

  @GET("api/v0/books/{book_id}")
  fun getBookDetail(@Path("book_id") bookId: Int?): Single<BaseResponse<Book>>

  @Multipart
  @POST("/api/v0/books")
  fun addBook(
      @PartMap params: Map<String, @JvmSuppressWildcards RequestBody?>): Single<BaseResponse<Book>>

  @GET("api/v0/books/add-owner/{book_id}")
  fun addUserHaveThisBook(@Path("book_id") bookId: Int?): Single<Any>

  @GET("api/v0/books/remove-owner/{book_id}")
  fun removeOwnerThisBook(@Path("book_id") bookId: Int?): Single<Any>

  @POST("api/v0/books/booking")
  fun readOrCancelBook(@Body readingOrCancelBookRequest: ReadingOrCancelBookRequest?): Single<Any>

  @POST("api/v0/users/add-tags")
  fun addCategoryFavorite(
      @Body addCategoryFavoriteRequest: AddCategoryFavoriteRequest?): Single<Any>

  @GET("/api/v0/books/category/{category_id}?office_id=1")
  fun getListBookByCategory(@Path(
      "category_id") categoryId: Int?): Single<BaseResponse<BaseBookByCategoryResponse>>

  @GET("/api/v0/books/sort-by/?office_id=1")
  fun getListSortBook(): Single<BaseResponse<List<SortBook>>>

  @POST("/api/v0/books/filters?")
  fun getListBookBySort(@Query("field") type: String?, @Query(
      "page") page: Int?, @Body sort: Sort?): Single<BaseResponse<BaseBookRespone<List<Book>>>>

  @GET("/api/v0/user/books/waiting_approve")
  fun getApproveRequest(): Single<BaseResponse<BaseBookRespone<List<Book>>>>

  @POST("/api/v0/books/review/{book_id}")
  fun reviewBook(
      @Path("book_id") bookId: Int?, @Body reviewBookRequest: ReviewBookRequest?): Single<Any>

  @POST("/api/v0/books/approve/{book_id}")
  fun userApproveBook(@Path("book_id") bookId: Int?,
      @Body userApproveBookRequest: UserApproveBookRequest?): Single<Any>

  @GET("/api/v0/user/{book_id}/approve/detail")
  fun getApproveDetail(@Path("book_id") bookId: Int?): Single<BaseResponse<Book>>

  @POST("/api/v0/refresh-token")
  fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest?): Single<TokenResponse>
}
