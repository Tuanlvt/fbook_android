package com.framgia.fbook.screen.listbookseemore

import com.framgia.fbook.data.model.Sort
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.CategoryRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Listens to user actions from the UI ([ListBookFragment]), retrieves the data and updates
 * the UI as required.
 */
open class ListBookPresenter(private val mCategoryRepository: CategoryRepository,
    private val mBookRepository: BookRepository) : ListBookContract.Presenter {

  private var mViewModel: ListBookContract.ViewModel? = null
  private lateinit var mSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun getListBookBySort(type: String?, page: Int?, sort: Sort?, officeId: Int?) {
    val disposable: Disposable = mBookRepository.getListBookBySort(type, page, sort, officeId)
        .subscribeOn(mSchedulerProvider.io())
        .doOnSubscribe {
          mViewModel?.let {
            if (it.isShowProgressDialog()) {
              it.onShowProgressBarDialog()
            }
          }
        }
        .doAfterTerminate { mViewModel?.onDismissProgressBarDialog() }
        .observeOn(mSchedulerProvider.ui())
        .subscribe(
            { listBookResponse ->
              mViewModel?.onGetListBookSuccess(listBookResponse.items?.data)
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  override fun getListSortBook() {
    val disposable: Disposable = mBookRepository.getListSortBook()
        .subscribeOn(mSchedulerProvider.io())
        .doOnSubscribe { mViewModel?.onShowProgressBarDialog() }
        .doAfterTerminate { mViewModel?.onDismissProgressBarDialog() }
        .observeOn(mSchedulerProvider.ui())
        .subscribe(
            { sortBookResponse ->
              mViewModel?.onGetListSortBookSuccess(sortBookResponse.items)
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  override fun getListBookByCategory(categoryId: Int?, officeId: Int?) {
    val disposable: Disposable = mCategoryRepository.getListBookByCategory(categoryId, officeId)
        .subscribeOn(mSchedulerProvider.io())
        .doOnSubscribe {
          mViewModel?.let {
            if (it.isShowProgressDialog()) {
              it.onShowProgressBarDialog()
            }
          }
        }
        .doAfterTerminate { mViewModel?.onDismissProgressBarDialog() }
        .observeOn(mSchedulerProvider.ui())
        .subscribe(
            { listBookResponse ->
              mViewModel?.onGetListBookSuccess(
                  listBookResponse.items?.categoryResponse?.data)
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  override fun getListCategory() {
    val disposable: Disposable = mCategoryRepository.getCategory()
        .subscribeOn(mSchedulerProvider.io())
        .observeOn(mSchedulerProvider.ui())
        .subscribe(
            { listCategory ->
              mViewModel?.onGetListCategorySuccess(listCategory.items)
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  override fun getListBook(typeBook: String?, page: Int?, officeId: Int?) {
    typeBook?.let {
      when (typeBook) {
        Constant.LATE -> {
          val disposable: Disposable =
              mBookRepository.getSectionListBook(Constant.LATE, page, officeId)
                  .subscribeOn(mSchedulerProvider.io())
                  .observeOn(mSchedulerProvider.ui())
                  .subscribe({ listBookLateResponse ->
                    mViewModel?.onGetListBookSuccess(listBookLateResponse.items?.data)
                  }, { error ->
                    mViewModel?.onError(error as BaseException)
                  })
          mCompositeDisposable.add(disposable)
        }
        Constant.VIEW -> {
          val disposable: Disposable =
              mBookRepository.getSectionListBook(Constant.VIEW, page, officeId)
                  .subscribeOn(mSchedulerProvider.io())
                  .observeOn(mSchedulerProvider.ui())
                  .subscribe({ listBookLateResponse ->
                    mViewModel?.onGetListBookSuccess(listBookLateResponse.items?.data)
                  }, { error ->
                    mViewModel?.onError(error as BaseException)
                  })
          mCompositeDisposable.add(disposable)
        }
        Constant.RATING -> {
          val disposable: Disposable =
              mBookRepository.getSectionListBook(Constant.RATING, page, officeId)
                  .subscribeOn(mSchedulerProvider.io())
                  .observeOn(mSchedulerProvider.ui())
                  .subscribe({ listBookLateResponse ->
                    mViewModel?.onGetListBookSuccess(listBookLateResponse.items?.data)
                  }, { error ->
                    mViewModel?.onError(error as BaseException)
                  })
          mCompositeDisposable.add(disposable)
        }
        Constant.WAITING -> {
          val disposable: Disposable =
              mBookRepository.getSectionListBook(Constant.WAITING, page, officeId)
                  .subscribeOn(mSchedulerProvider.io())
                  .observeOn(mSchedulerProvider.ui())
                  .subscribe({ listBookLateResponse ->
                    mViewModel?.onGetListBookSuccess(listBookLateResponse.items?.data)
                  }, { error ->
                    mViewModel?.onError(error as BaseException)
                  })
          mCompositeDisposable.add(disposable)
        }
        Constant.READ -> {
          val disposable: Disposable =
              mBookRepository.getSectionListBook(Constant.READ, page, officeId)
                  .subscribeOn(mSchedulerProvider.io())
                  .observeOn(mSchedulerProvider.ui())
                  .subscribe({ listBookLateResponse ->
                    mViewModel?.onGetListBookSuccess(listBookLateResponse.items?.data)
                  }, { error ->
                    mViewModel?.onError(error as BaseException)
                  })
          mCompositeDisposable.add(disposable)
        }
        else -> {
        }
      }
    }
  }

  override fun setViewModel(viewModel: ListBookContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider) {
    mSchedulerProvider = schedulerProvider
  }

  companion object {
    private val TAG = ListBookPresenter::class.java.name
  }
}
