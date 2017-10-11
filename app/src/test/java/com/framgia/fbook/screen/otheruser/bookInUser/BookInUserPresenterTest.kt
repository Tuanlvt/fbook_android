package com.framgia.fbook.screen.otheruser.bookInUser

import com.framgia.fbook.data.model.ActionBookDetail
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.error.Type
import com.framgia.fbook.data.source.remote.api.response.BaseBookRespone
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
class BookInUserPresenterTest {
  @InjectMocks
  lateinit var mPresenter: BookInUserPresenter
  @InjectMocks
  lateinit var mBaseSchedulerProvider: ImmediateSchedulerProvider
  @Mock
  lateinit var mViewModel: BookInUserFragment
  @Mock
  lateinit var mBookRepository: BookRepository
  private val mBookResponse: BaseResponse<BaseBookRespone<List<Book>>> = BaseResponse()
  private val mBaseException = BaseException(Type.HTTP, ErrorResponse())
  private val mActionBookDetail = ActionBookDetail()
  private val mUserId = 32
  private val mType = "waiting"


  @Before
  fun setUp() {
    mPresenter.setViewModel(mViewModel)
    mPresenter.setSchedulerProvider(mBaseSchedulerProvider)
  }

  @Test
  fun getBookInUserProfile_shouldReturnSuccess_whenGetBookInUserProfile() {
    Mockito.`when`(mBookRepository.getFeatureOtherOfUser(mUserId, mType)).thenReturn(
        Single.just(mBookResponse))
    mPresenter.getBookInUserProfile(mUserId, mType)

    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    mBookResponse.items?.data?.let { Mockito.verify(mViewModel).onGetBookInUserProfileSuccess(it) }
  }

  @Test
  fun getBookInUserProfile_shouldReturnError_whenGetBookInUserProfile() {
    Mockito.`when`(mBookRepository.getFeatureOtherOfUser(mUserId, mType)).thenReturn(
        Single.error(mBaseException))
    mPresenter.getBookInUserProfile(mUserId, mType)

    mBookResponse.items?.data?.let {
      Mockito.verify(mViewModel, Mockito.never()).onGetBookInUserProfileSuccess(it)
    }
    Mockito.verify(mViewModel).onError(mBaseException)
  }

  @Test
  fun returnBook_shouldReturnSuccess_whenReturnBook() {
    mActionBookDetail.bookId = 1
    mActionBookDetail.ownerId = 2
    mActionBookDetail.status = 3
    Mockito.`when`(mBookRepository.returnBook(mActionBookDetail)).thenReturn(
        Single.just(mBookResponse))
    mPresenter.returnBook(mActionBookDetail)
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onReturnBookSuccess()
  }

  @Test
  fun returnBook_shouldReturnError_whenReturnBook() {
    mActionBookDetail.bookId = 1
    mActionBookDetail.ownerId = 2
    mActionBookDetail.status = 3
    Mockito.`when`(mBookRepository.returnBook(mActionBookDetail)).thenReturn(
        Single.error(mBaseException))
    mPresenter.returnBook(mActionBookDetail)
    Mockito.verify(mViewModel, Mockito.never()).onReturnBookSuccess()
    Mockito.verify(mViewModel).onError(mBaseException)
  }
}
