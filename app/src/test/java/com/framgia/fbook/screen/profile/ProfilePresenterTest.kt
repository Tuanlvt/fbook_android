package com.framgia.fbook.screen.profile

import com.framgia.fbook.data.model.Follow
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.error.Type
import com.framgia.fbook.data.source.remote.api.request.FollowOrUnFollowUserRequest
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
class ProfilePresenterTest {
  @InjectMocks
  lateinit var mPresenter: ProfilePresenter
  @InjectMocks
  lateinit var mBaseSchedulerProvider: ImmediateSchedulerProvider
  @Mock
  lateinit var mViewModel: ProfileActivity
  @Mock
  lateinit var mUserRepository: UserRepository
  private val mFollowResponse: BaseResponse<Follow> = BaseResponse()
  private val mUserResponse: BaseResponse<User> = BaseResponse()
  private val mBaseException = BaseException(Type.HTTP, ErrorResponse())
  private val mFollowRequest = FollowOrUnFollowUserRequest()
  private val mUserId: Int = 32

  @Before
  fun setUp() {
    mPresenter.setViewModel(mViewModel)
    mPresenter.setSchedulerProvider(mBaseSchedulerProvider)
  }

  @Test
  fun getUserOtherProfile_shouldReturnSuccess_whenGetUserOtherProfile() {
    Mockito.`when`(mUserRepository.getOtherUserProfile(mUserId)).thenReturn(
        Single.just(mUserResponse))
    mPresenter.getUserOtherProfile(mUserId)
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onGetUserOtherProfileSuccess(mUserResponse.items)
  }

  @Test
  fun getUserOtherProfile_shouldReturnError_whenGetUserOtherProfile() {
    Mockito.`when`(mUserRepository.getOtherUserProfile(mUserId)).thenReturn(
        Single.error(mBaseException))
    mPresenter.getUserOtherProfile(mUserId)
    Mockito.verify(mViewModel, Mockito.never()).onGetUserOtherProfileSuccess(mUserResponse.items)
    Mockito.verify(mViewModel).onError(mBaseException)
  }


  @Test
  fun getFollowInfomationOfUser_shouldReturnSuccess_whenGetFollowInformation() {
    Mockito.`when`(mUserRepository.getFollowInfomationOfUser(mUserId)).thenReturn(
        Single.just(mFollowResponse))
    mPresenter.getFollowInfomationOfUser(mUserId)
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onGetFollowInfomationOfUserSuccess(mFollowResponse.items)
  }

  @Test
  fun getFollowInfomationOfUser_shouldReturnError_whenGetFollowInformation() {
    Mockito.`when`(mUserRepository.getFollowInfomationOfUser(mUserId)).thenReturn(
        Single.error(mBaseException))
    mPresenter.getFollowInfomationOfUser(mUserId)
    Mockito.verify(mViewModel, Mockito.never()).onGetFollowInfomationOfUserSuccess(
        mFollowResponse.items)
    Mockito.verify(mViewModel).onError(mBaseException)
  }

  @Test
  fun followOrUnFollow_shouldReturnSuccess_whenFollowOrUnFollow() {
    mFollowRequest.item?.userId = mUserId
    Mockito.`when`(mUserRepository.followOrUnFollowUser(mFollowRequest)).thenReturn(
        Single.just(mFollowResponse))
    mPresenter.followOrUnFollow(mFollowRequest)
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onFollowOrUnFollowSuccess()
  }

  @Test
  fun followOrUnFollow_shouldReturnError_whenFollowOrUnFollow() {
    mFollowRequest.item?.userId = mUserId
    Mockito.`when`(mUserRepository.followOrUnFollowUser(mFollowRequest)).thenReturn(
        Single.error(mBaseException))
    mPresenter.followOrUnFollow(mFollowRequest)
    Mockito.verify(mViewModel, Mockito.never()).onFollowOrUnFollowSuccess()
    Mockito.verify(mViewModel).onError(mBaseException)
  }
}
