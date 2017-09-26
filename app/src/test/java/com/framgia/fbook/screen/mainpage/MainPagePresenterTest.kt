package com.framgia.fbook.screen.mainpage

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.UserRepository
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
 * Created by Hyperion on 9/14/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
@RunWith(MockitoJUnitRunner::class)
class MainPagePresenterTest {

  @InjectMocks
  lateinit var mPresenter: MainPagePresenter
  @Mock
  lateinit var mViewModel: MainPageFragment
  @InjectMocks
  lateinit var mBaseSchedulerProvider: ImmediateSchedulerProvider
  @Mock
  lateinit var mBookRepository: BookRepository
  @Mock
  lateinit var mUserRepository: UserRepository
  private val LATE = "latest"
  private val VIEW = "view"
  private val RATING = "rating"
  private val WAITING = "waiting"
  private val READ = "read"
  private val PAGE = 1
  private val baseException = BaseException(Type.HTTP, ErrorResponse())
  private val listResponse: BaseResponse<BaseBookRespone<List<Book>>> = BaseResponse()
  @Before
  fun setUp() {
    mPresenter.setViewModel(mViewModel)
    mPresenter.setSchedulerProvider(mBaseSchedulerProvider)
  }

  @Test
  fun getSectionListBook_shouldReturnListBook_whenGetSectionListBook() {
    Mockito.`when`(mBookRepository.getSectionListBook(LATE, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(RATING, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(VIEW, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(WAITING, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(READ, PAGE))
        .thenReturn(Single.just(listResponse))
    mPresenter.getSectionListBook()
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.LATE_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.RATING_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.VIEW_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.WAITING_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.READ_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel, Mockito.never()).onError(baseException)
  }

  @Test
  fun getSectionListBook_shouldReturnError_whenGetSectionListLateBook() {
    Mockito.`when`(mBookRepository.getSectionListBook(LATE, PAGE))
        .thenReturn(Single.error(baseException))
    mPresenter.getSectionListBook()
    Mockito.verify(mViewModel, Mockito.never()).onGetSectionListBookSuccess(TypeBook.LATE_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onError(baseException)
  }

  @Test
  fun getSectionListBook_shouldReturnError_whenGetSectionListRatingBook() {
    Mockito.`when`(mBookRepository.getSectionListBook(LATE, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(RATING, PAGE))
        .thenReturn(Single.error(baseException))
    mPresenter.getSectionListBook()
    Mockito.verify(mViewModel, Mockito.never()).onGetSectionListBookSuccess(TypeBook.READ_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.LATE_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onError(baseException)
  }

  @Test
  fun getSectionListBook_shouldReturnError_whenGetSectionListViewBook() {
    Mockito.`when`(mBookRepository.getSectionListBook(LATE, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(RATING, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(VIEW, PAGE))
        .thenReturn(Single.error(baseException))
    mPresenter.getSectionListBook()
    Mockito.verify(mViewModel, Mockito.never()).onGetSectionListBookSuccess(TypeBook.READ_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.LATE_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.RATING_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onError(baseException)
  }

  @Test
  fun getSectionListBook_shouldReturnError_whenGetSectionWaitingViewBook() {
    Mockito.`when`(mBookRepository.getSectionListBook(LATE, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(RATING, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(VIEW, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(WAITING, PAGE))
        .thenReturn(Single.error(baseException))
    mPresenter.getSectionListBook()
    Mockito.verify(mViewModel, Mockito.never()).onGetSectionListBookSuccess(TypeBook.READ_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.LATE_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.RATING_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.VIEW_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onError(baseException)
  }

  @Test
  fun getSectionListBook_shouldReturnError_whenGetSectionReadViewBook() {
    Mockito.`when`(mBookRepository.getSectionListBook(LATE, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(RATING, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(VIEW, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(WAITING, PAGE))
        .thenReturn(Single.just(listResponse))
    Mockito.`when`(mBookRepository.getSectionListBook(READ, PAGE))
        .thenReturn(Single.error(baseException))
    mPresenter.getSectionListBook()
    Mockito.verify(mViewModel, Mockito.never()).onGetSectionListBookSuccess(TypeBook.READ_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.LATE_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.RATING_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.VIEW_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onGetSectionListBookSuccess(TypeBook.WAITING_BOOK,
        listResponse.item?.data)
    Mockito.verify(mViewModel).onError(baseException)
  }
}
