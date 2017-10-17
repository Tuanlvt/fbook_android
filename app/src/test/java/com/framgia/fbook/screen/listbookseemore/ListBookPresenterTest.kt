package com.framgia.fbook.screen.listbookseemore

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.Sort
import com.framgia.fbook.data.model.SortBook
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.CategoryRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.error.Type
import com.framgia.fbook.data.source.remote.api.response.BaseBookByCategoryResponse
import com.framgia.fbook.data.source.remote.api.response.BaseBookRespone
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.data.source.remote.api.response.ErrorResponse
import com.framgia.fbook.utils.Constant
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
class ListBookPresenterTest {

  @InjectMocks
  lateinit var mPresenter: ListBookPresenter
  @Mock
  lateinit var mViewModel: ListBookFragment
  @InjectMocks
  lateinit var mBaseSchedulerProvider: ImmediateSchedulerProvider
  @Mock
  lateinit var mBookRepository: BookRepository
  @Mock
  lateinit var mCategoryRepository: CategoryRepository
  private val listResponse: BaseResponse<BaseBookRespone<List<Book>>> = BaseResponse()
  private val listCategory: BaseResponse<List<Category>> = BaseResponse()
  private val listBookByCategory: BaseResponse<BaseBookByCategoryResponse> = BaseResponse()
  private val listSortBook: BaseResponse<List<SortBook>> = BaseResponse()
  private val baseException = BaseException(Type.HTTP, ErrorResponse())
  private val officeID = 1

  @Before
  fun setUp() {
    mPresenter.setViewModel(mViewModel)
    mPresenter.setSchedulerProvider(mBaseSchedulerProvider)
  }

  @Test
  fun getListBook_shouldReturnListLateBook_whenTypeBookIsLate() {
    Mockito.`when`(mBookRepository.getSectionListBook(Constant.LATE, Constant.PAGE, officeID))
        .thenReturn(Single.just(listResponse))
    mPresenter.getListBook(Constant.LATE, Constant.PAGE, officeID)
    Mockito.verify(mViewModel).onGetListBookSuccess(listResponse.items?.data)
    Mockito.verify(mViewModel, Mockito.never()).onError(baseException)
  }

  @Test
  fun getListBook_shouldReturnListRatingBook_whenTypeBookIsRating() {
    Mockito.`when`(mBookRepository.getSectionListBook(Constant.RATING, Constant.PAGE, officeID))
        .thenReturn(Single.just(listResponse))
    mPresenter.getListBook(Constant.RATING, Constant.PAGE, officeID)
    Mockito.verify(mViewModel).onGetListBookSuccess(listResponse.items?.data)
    Mockito.verify(mViewModel, Mockito.never()).onError(baseException)
  }

  @Test
  fun getListBook_shouldReturnListViewBook_whenTypeBookIsView() {
    Mockito.`when`(mBookRepository.getSectionListBook(Constant.VIEW, Constant.PAGE, officeID))
        .thenReturn(Single.just(listResponse))
    mPresenter.getListBook(Constant.VIEW, Constant.PAGE, officeID)
    Mockito.verify(mViewModel).onGetListBookSuccess(listResponse.items?.data)
    Mockito.verify(mViewModel, Mockito.never()).onError(baseException)
  }

  @Test
  fun getListBook_shouldReturnListLWaitingBook_whenTypeBookIsWaiting() {
    Mockito.`when`(mBookRepository.getSectionListBook(Constant.WAITING, Constant.PAGE, officeID))
        .thenReturn(Single.just(listResponse))
    mPresenter.getListBook(Constant.WAITING, Constant.PAGE, officeID)
    Mockito.verify(mViewModel).onGetListBookSuccess(listResponse.items?.data)
    Mockito.verify(mViewModel, Mockito.never()).onError(baseException)
  }

  @Test
  fun getListBook_shouldReturnListReadBook_whenTypeBookIsRead() {
    Mockito.`when`(mBookRepository.getSectionListBook(Constant.READ, Constant.PAGE, officeID))
        .thenReturn(Single.just(listResponse))
    mPresenter.getListBook(Constant.READ, Constant.PAGE, officeID)
    Mockito.verify(mViewModel).onGetListBookSuccess(listResponse.items?.data)
    Mockito.verify(mViewModel, Mockito.never()).onError(baseException)
  }

  @Test
  fun getListBook_shouldReturnError_whenGetListBook() {
    Mockito.`when`(mBookRepository.getSectionListBook(Constant.READ, Constant.PAGE, officeID))
        .thenReturn(Single.error(baseException))
    mPresenter.getListBook(Constant.READ, Constant.PAGE, officeID)
    Mockito.verify(mViewModel, Mockito.never()).onGetListBookSuccess(listResponse.items?.data)
    Mockito.verify(mViewModel).onError(baseException)
  }

  @Test
  fun getListCategory_shouldReturnListCategory_whenGetListCategory() {
    Mockito.`when`(mCategoryRepository.getCategory()).thenReturn(Single.just(listCategory))
    mPresenter.getListCategory()
    Mockito.verify(mViewModel).onGetListCategorySuccess(listCategory.items)
    Mockito.verify(mViewModel, Mockito.never()).onError(baseException)
  }

  @Test
  fun getListCategory_shouldReturnError_whenGetListCategory() {
    Mockito.`when`(mCategoryRepository.getCategory()).thenReturn(Single.error(baseException))
    mPresenter.getListCategory()
    Mockito.verify(mViewModel, Mockito.never()).onGetListCategorySuccess(listCategory.items)
    Mockito.verify(mViewModel).onError(baseException)
  }

  @Test
  fun getListBookByCategory_shouldReturnListBook_whenGetListBookByCategory() {
    val categoryId = 1
    Mockito.`when`(mCategoryRepository.getListBookByCategory(categoryId, officeID)).thenReturn(
        Single.just(listBookByCategory))
    mPresenter.getListBookByCategory(categoryId, officeID)
    Mockito.verify(mViewModel).onGetListBookSuccess(
        listBookByCategory.items?.categoryResponse?.data)
    Mockito.verify(mViewModel, Mockito.never()).onError(baseException)
  }

  @Test
  fun getListBookByCategory_shouldReturnError_whenGetListBookByCategory() {
    val categoryId = 1
    Mockito.`when`(mCategoryRepository.getListBookByCategory(categoryId, officeID)).thenReturn(
        Single.error(baseException))
    mPresenter.getListBookByCategory(categoryId, officeID)
    Mockito.verify(mViewModel, Mockito.never()).onGetListBookSuccess(
        listBookByCategory.items?.categoryResponse?.data)
    Mockito.verify(mViewModel).onError(baseException)
  }

  @Test
  fun getListSortBook_shouldReturnListSort_whenGetListSortBook() {
    Mockito.`when`(mBookRepository.getListSortBook()).thenReturn(Single.just(listSortBook))
    mPresenter.getListSortBook()
    Mockito.verify(mViewModel).onGetListSortBookSuccess(listSortBook.items)
    Mockito.verify(mViewModel, Mockito.never()).onError(baseException)
  }

  @Test
  fun getListSortBook_shouldReturnError_whenGetListSortBook() {
    Mockito.`when`(mBookRepository.getListSortBook()).thenReturn(Single.error(baseException))
    mPresenter.getListSortBook()
    Mockito.verify(mViewModel, Mockito.never()).onGetListSortBookSuccess(listSortBook.items)
    Mockito.verify(mViewModel).onError(baseException)
  }

  @Test
  fun getListBookBySort_shouldReturnListBook_whenGetListBySort() {
    val sort = Sort()
    Mockito.`when`(
        mBookRepository.getListBookBySort(Constant.LATE, Constant.PAGE, sort, officeID)).thenReturn(
        Single.just(listResponse))
    mPresenter.getListBookBySort(Constant.LATE, Constant.PAGE, sort, officeID)
    Mockito.verify(mViewModel).onGetListBookSuccess(listResponse.items?.data)
    Mockito.verify(mViewModel, Mockito.never()).onError(baseException)
  }

  @Test
  fun getListBookBySort_shouldReturnError_whenGetListBySort() {
    val sort = Sort()
    Mockito.`when`(
        mBookRepository.getListBookBySort(Constant.LATE, Constant.PAGE, sort, officeID)).thenReturn(
        Single.error(baseException))
    mPresenter.getListBookBySort(Constant.LATE, Constant.PAGE, sort, officeID)
    Mockito.verify(mViewModel, Mockito.never()).onGetListBookSuccess(listResponse.items?.data)
    Mockito.verify(mViewModel).onError(baseException)
  }
}
