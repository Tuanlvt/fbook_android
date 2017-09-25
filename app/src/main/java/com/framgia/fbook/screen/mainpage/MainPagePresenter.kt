package com.framgia.fbook.screen.mainpage

import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Listens to user actions from the UI ([MainPageFragment]), retrieves the data and updates
 * the UI as required.
 */
open class MainPagePresenter(private val mUserRepository: UserRepository,
    private val mBookRepository: BookRepository) : MainPageContract.Presenter {

  private lateinit var mViewModel: MainPageContract.ViewModel
  private lateinit var mSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun getListOffice() {
    val disposable = mUserRepository.getOffices()
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe({ officeResponse ->
          mViewModel.onGetOfficeSuccess(officeResponse.items)
        }, { error ->
          mViewModel.onError(error as BaseException)
        })
    mCompositeDisposable.add(disposable)
  }

  override fun getSectionListBook() {
    val disposable: Disposable = mBookRepository.getSectionListBook(Constant.LATE, Constant.PAGE)
        .subscribeOn(mSchedulerProvider.io())
        .doOnSubscribe { mViewModel.onShowProgressDialog() }
        .doAfterTerminate { mViewModel.onDismissProgressDialog() }
        .flatMap { listBookLateResponse ->
          mViewModel.onGetSectionListBookSuccess(TypeBook.LATE_BOOK,
              listBookLateResponse.items?.data)
          mBookRepository.getSectionListBook(Constant.RATING, Constant.PAGE)
        }
        .flatMap { listBookRatingResponse ->
          mViewModel.onGetSectionListBookSuccess(TypeBook.RATING_BOOK,
              listBookRatingResponse.items?.data)
          mBookRepository.getSectionListBook(Constant.VIEW, Constant.PAGE)
        }
        .flatMap { listBookViewResponse ->
          mViewModel.onGetSectionListBookSuccess(TypeBook.VIEW_BOOK,
              listBookViewResponse.items?.data)
          mBookRepository.getSectionListBook(Constant.WAITING, Constant.PAGE)
        }
        .flatMap { listBookWaitingResponse ->
          mViewModel.onGetSectionListBookSuccess(TypeBook.WAITING_BOOK,
              listBookWaitingResponse.items?.data)
          mBookRepository.getSectionListBook(Constant.READ, Constant.PAGE)
        }
        .observeOn(mSchedulerProvider.ui())
        .subscribe({ listBookReadResponse ->
          mViewModel.onGetSectionListBookSuccess(TypeBook.READ_BOOK,
              listBookReadResponse.items?.data)
        }, { error ->
          if (error is BaseException) {
            mViewModel.onError(error)
          }
        })
    mCompositeDisposable.add(disposable)
  }

  override fun setViewModel(viewModel: MainPageContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider) {
    mSchedulerProvider = schedulerProvider
  }

  companion object {
    private val TAG = MainPagePresenter::class.java.name
  }
}
