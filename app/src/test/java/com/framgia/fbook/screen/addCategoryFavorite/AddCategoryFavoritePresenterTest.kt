package com.framgia.fbook.screen.addCategoryFavorite

import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.source.CategoryRepository
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.error.Type
import com.framgia.fbook.data.source.remote.api.request.AddCategoryFavoriteRequest
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.data.source.remote.api.response.ErrorResponse
import com.framgia.fbook.utils.rx.ImmediateSchedulerProvider
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Tuanlvt on 11/10/2017.
 * Contact me tantuan127@gmail.com.
 * Thank you !
 */
@RunWith(MockitoJUnitRunner::class)
class AddCategoryFavoritePresenterTest {
  @InjectMocks
  lateinit var mPresenter: AddCategoryFavoritePresenter
  @InjectMocks
  lateinit var mBaseSchedulerProvider: ImmediateSchedulerProvider
  @Mock
  lateinit var mViewModel: AddCategoryFavoriteActivity
  @Mock
  lateinit var mCategoryRepository: CategoryRepository
  @Mock
  lateinit var mUserRepository: UserRepository
  private val mCategoryResponse: BaseResponse<List<Category>> = BaseResponse()
  private val mBaseException = BaseException(Type.HTTP, ErrorResponse())
  private val mAddCategoryRequest = AddCategoryFavoriteRequest()


  @Before
  fun setUp() {
    mPresenter.setViewModel(mViewModel)
    mPresenter.setSchedulerProvider(mBaseSchedulerProvider)
  }

  @Test
  fun getCategory_shouldReturnSuccess_whenGetCategory() {
    Mockito.`when`(mCategoryRepository.getCategory()).thenReturn(
        Single.just(mCategoryResponse))
    mPresenter.getCategory()
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onGetCategorySuccess(mCategoryResponse.items)
  }

  @Test
  fun getCategory_shouldReturnError_whenGetCategory() {
    Mockito.`when`(mCategoryRepository.getCategory()).thenReturn(
        Single.error(mBaseException))
    mPresenter.getCategory()
    Mockito.verify(mViewModel, Mockito.never()).onGetCategorySuccess(mCategoryResponse.items)
    Mockito.verify(mViewModel).onError(mBaseException)
  }

  @Test
  fun updateCategory_shouldReturnSuccess_whenUpdateCategory() {
    mAddCategoryRequest.item?.tags = "1,2,5"
    Mockito.`when`(mCategoryRepository.addCategoryFavorite(mAddCategoryRequest)).thenReturn(
        Single.just(mCategoryResponse))
    mPresenter.updateCategory(mAddCategoryRequest)
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onUpdateCategoryFavoriteSuccess()
  }

  @Test
  fun updateCategory_shouldReturnError_whenUpdateCategory() {
    mAddCategoryRequest.item?.tags = "1,2,5"
    Mockito.`when`(mCategoryRepository.addCategoryFavorite(mAddCategoryRequest)).thenReturn(
        Single.error(mBaseException))
    mPresenter.updateCategory(mAddCategoryRequest)
    Mockito.verify(mViewModel, Mockito.never()).onUpdateCategoryFavoriteSuccess()
    Mockito.verify(mViewModel).onError(mBaseException)
  }
}
