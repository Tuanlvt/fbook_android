package com.framgia.fbook.screen.sharebook

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.GoogleBook
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.CategoryRepository
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.error.Type
import com.framgia.fbook.data.source.remote.api.request.BookRequest
import com.framgia.fbook.data.source.remote.api.response.BaseBookRespone
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.data.source.remote.api.response.ErrorResponse
import com.framgia.fbook.utils.rx.ImmediateSchedulerProvider
import com.framgia.fbook.utils.validator.Validator
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by levutantuan on 10/16/17.
 */
@RunWith(MockitoJUnitRunner::class)
class ShareBookPresenterTest {
  @InjectMocks
  lateinit var mPresenter: ShareBookPresenter
  @InjectMocks
  lateinit var mBaseSchedulerProvider: ImmediateSchedulerProvider
  @Mock
  lateinit var mViewModel: ShareBookActivity
  @Mock
  lateinit var mUserRepository: UserRepository
  @Mock
  lateinit var mCategoryRepository: CategoryRepository
  @Mock
  lateinit var mBookRepository: BookRepository
  @Mock
  lateinit var mValidator: Validator
  private val mBaseException = BaseException(Type.HTTP, ErrorResponse())
  private val mUserResponse: BaseResponse<List<Office>> = BaseResponse()
  private val mCategoryResponse: BaseResponse<List<Category>> = BaseResponse()
  private val mBookInternalResponse: BaseResponse<BaseBookRespone<List<Book>>> = BaseResponse()
  private val mBookGoogleResponse: BaseResponse<List<GoogleBook>> = BaseResponse()
  private val mAddBookResponse: BaseResponse<Book> = BaseResponse()
  private val mAddBookRequest = BookRequest()
  private val mKeyWord: String = "title"
  private val mField: String = "code"
  @Before
  fun setUp() {
    mPresenter.setViewModel(mViewModel)
    mPresenter.setSchedulerProvider(mBaseSchedulerProvider)
  }

  @Test
  fun getData_shouldGetDataSuccess_whenGetData() {
    Mockito.`when`(mUserRepository.getOffices()).thenReturn(Single.just(mUserResponse))
    Mockito.`when`(mCategoryRepository.getCategory()).thenReturn(Single.just(mCategoryResponse))
    mPresenter.getData()
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onGetOfficeSuccess(mUserResponse.items)
    Mockito.verify(mViewModel).onGetCategorySuccess(mCategoryResponse.items)
  }


  @Test
  fun getData_shouldGetOfficeError_whenGetData() {
    Mockito.`when`(mUserRepository.getOffices()).thenReturn(Single.error(mBaseException))
    Mockito.`when`(mCategoryRepository.getCategory()).thenReturn(Single.just(mCategoryResponse))
    mPresenter.getData()
    Mockito.verify(mViewModel, Mockito.never()).onGetOfficeSuccess(mUserResponse.items)
    Mockito.verify(mViewModel, Mockito.never()).onGetCategorySuccess(mCategoryResponse.items)
    Mockito.verify(mViewModel).onError(mBaseException)
  }

  @Test
  fun getData_shouldGetCategoryError_whenGetData() {
    Mockito.`when`(mCategoryRepository.getCategory()).thenReturn(Single.error(mBaseException))
    Mockito.`when`(mUserRepository.getOffices()).thenReturn(Single.just(mUserResponse))
    mPresenter.getData()
    Mockito.verify(mViewModel, Mockito.never()).onGetOfficeSuccess(mUserResponse.items)
    Mockito.verify(mViewModel, Mockito.never()).onGetCategorySuccess(mCategoryResponse.items)
    Mockito.verify(mViewModel).onError(mBaseException)
  }

  @Test
  fun getData_shouldGetDataError_whenGetData() {
    Mockito.`when`(mUserRepository.getOffices()).thenReturn(Single.error(mBaseException))
    Mockito.`when`(mCategoryRepository.getCategory()).thenReturn(Single.error(mBaseException))
    mPresenter.getData()
    Mockito.verify(mViewModel, Mockito.never()).onGetOfficeSuccess(mUserResponse.items)
    Mockito.verify(mViewModel, Mockito.never()).onGetCategorySuccess(mCategoryResponse.items)
    Mockito.verify(mViewModel).onError(mBaseException)
  }

  @Test
  fun addBook_shouldAddBookSuccess_whenAddBook() {
    onAddDataBook()
    Mockito.`when`(mBookRepository.addBook(mAddBookRequest)).thenReturn(
        Single.just(mAddBookResponse))
    mPresenter.addBook(mAddBookRequest)
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onAddBookSuccess(mAddBookResponse.items)
  }

  @Test
  fun addBook_shouldAddBookError_whenAddBook() {
    onAddDataBook()
    Mockito.`when`(mBookRepository.addBook(mAddBookRequest)).thenReturn(
        Single.error(mBaseException))
    mPresenter.addBook(mAddBookRequest)
    Mockito.verify(mViewModel, Mockito.never()).onAddBookSuccess(mAddBookResponse.items)
    Mockito.verify(mViewModel).onError(mBaseException)

  }

  @Test
  fun searchBookFromInternal_shouldSearchBookFromInternalSuccess_whenSearchBookFromInternal() {
    Mockito.`when`(mBookRepository.searchBook(mKeyWord, mField)).thenReturn(
        Single.just(mBookInternalResponse))
    mPresenter.searchBookFromInternal(mKeyWord, mField)
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onSearchBookFromInternalSuccess(mBookInternalResponse.items?.data)
  }

  @Test
  fun searchBookFromInternal_shouldSearchBookFromInternalError_whenSearchBookFromInternal() {
    Mockito.`when`(mBookRepository.searchBook(mKeyWord, mField)).thenReturn(
        Single.error(mBaseException))
    mPresenter.searchBookFromInternal(mKeyWord, mField)
    Mockito.verify(mViewModel, Mockito.never()).onSearchBookFromInternalSuccess(
        mBookInternalResponse.items?.data)
    Mockito.verify(mViewModel).onError(mBaseException)
  }

  @Test
  fun searchBookFromGoogleBook_shouldSearchBookFromGoogleBookSuccess_whenSearchBookFromGoogleBook() {
    Mockito.`when`(mBookRepository.searchBookWithGoogleApi(mField)).thenReturn(
        Single.just(mBookGoogleResponse))
    mPresenter.searchBookFromGoogleBook(mField)
    Mockito.verify(mViewModel, Mockito.never()).onError(mBaseException)
    Mockito.verify(mViewModel).onSearchBookFromGoogleBookSuccess(mBookGoogleResponse.items)
  }

  @Test
  fun searchBookFromGoogleBook_shouldSearchBookFromGoogleBookError_whenSearchBookFromGoogleBook() {
    Mockito.`when`(mBookRepository.searchBookWithGoogleApi(mField)).thenReturn(
        Single.error(mBaseException))
    mPresenter.searchBookFromGoogleBook(mField)
    Mockito.verify(mViewModel, Mockito.never()).onSearchBookFromGoogleBookSuccess(
        mBookGoogleResponse.items)
    Mockito.verify(mViewModel).onError(mBaseException)
  }

  fun onAddDataBook() {
    mAddBookRequest.title = "Sana"
    mAddBookRequest.author = "Nguyen Van A"
    mAddBookRequest.categoryId = 1
    mAddBookRequest.description = "Sana Sana Sana"
    mAddBookRequest.officeId = 2
    mAddBookRequest.publishDate = "2017-10-16"
  }
}
