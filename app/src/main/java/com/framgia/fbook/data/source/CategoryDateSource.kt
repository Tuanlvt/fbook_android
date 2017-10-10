package com.framgia.fbook.data.source

import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.source.remote.api.request.AddCategoryFavoriteRequest
import com.framgia.fbook.data.source.remote.api.response.BaseBookByCategoryResponse
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import io.reactivex.Single

/**
 * Created by levutantuan on 9/11/17.
 */
interface CategoryDateSource {

  interface CategoryRemoteDataSource {

    fun getCategory(): Single<BaseResponse<List<Category>>>

    fun addCategoryFavorite(addCategoryFavoriteRequest: AddCategoryFavoriteRequest?): Single<Any>

    fun getListBookByCategory(categoryId: Int?,
        officeId: Int?): Single<BaseResponse<BaseBookByCategoryResponse>>
  }
}
