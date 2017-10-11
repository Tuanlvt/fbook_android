package com.framgia.fbook.data.source.remote

import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.source.CategoryDateSource
import com.framgia.fbook.data.source.remote.api.request.AddCategoryFavoriteRequest
import com.framgia.fbook.data.source.remote.api.response.BaseBookByCategoryResponse
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.data.source.remote.api.service.FBookApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by levutantuan on 9/11/17.
 */
class CategoryRemoteDataSource @Inject constructor(nameApi: FBookApi) : BaseRemoteDataSource(
    nameApi), CategoryDateSource.CategoryRemoteDataSource {

  override fun addCategoryFavorite(
      addCategoryFavoriteRequest: AddCategoryFavoriteRequest?): Single<Any> {
    return fbookApi.addCategoryFavorite(addCategoryFavoriteRequest)
  }

  override fun getCategory(): Single<BaseResponse<List<Category>>> {
    return fbookApi.getCategory()
  }

  override fun getListBookByCategory(
      categoryId: Int?, officeId: Int?): Single<BaseResponse<BaseBookByCategoryResponse>> {
    return fbookApi.getListBookByCategory(categoryId, officeId)
  }
}
