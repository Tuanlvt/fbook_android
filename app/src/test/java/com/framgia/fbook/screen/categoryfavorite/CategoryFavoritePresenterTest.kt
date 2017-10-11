package com.framgia.fbook.screen.categoryfavorite

import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.error.Type
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
class CategoryFavoritePresenterTest {
  @InjectMocks
  lateinit var mPresenter: CategoryFavoritePresenter
  @InjectMocks
  lateinit var mSchedulerProvider: ImmediateSchedulerProvider
  @Mock
  lateinit var mViewModel: CategoryFavoriteFragment
  @Mock
  lateinit var mUserRepository: UserRepository

  private val mUserResponse: BaseResponse<User> = BaseResponse()
  private val mBaseException = BaseException(Type.HTTP, ErrorResponse())

  @Before
  fun setUp() {
    mPresenter.setViewModel(mViewModel)
    mPresenter.setBaseSchedulerProvider(mSchedulerProvider)
  }

  @Test
  fun getUser_shouldReturnSuccess_whengetUser() {
    Mockito.`when`(mUserRepository.getUser()).thenReturn(Single.just(mUserResponse))
    mPresenter.getUser()
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onGetUserSuccess(mUserResponse.items)
  }

  @Test
  fun getUser_shouldReturnError_whengetUser() {
    Mockito.`when`(mUserRepository.getUser()).thenReturn(Single.error(mBaseException))
    mPresenter.getUser()
    Mockito.verify(mViewModel, Mockito.never()).onGetUserSuccess(mUserResponse.items)
    Mockito.verify(mViewModel).onError(mBaseException)
  }
}
