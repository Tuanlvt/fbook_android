package com.framgia.fbook.screen.sharebook;

import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.model.OfficesAndCategories
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.CategoryRepository
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.BookRequest
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.utils.common.StringUtils
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.framgia.fbook.utils.validator.Validator
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

/**
 * Listens to user actions from the UI ({@link ShareBookActivity}), retrieves the data and updates
 * the UI as required.
 */
open class ShareBookPresenter(private val mValidator: Validator,
    private val mUserRepository: UserRepository,
    private val mCategoryRepository: CategoryRepository,
    private val mBookRepository: BookRepository) : ShareBookContract.Presenter {
  companion object {
    private val TAG = ShareBookPresenter::class.java.name
  }

  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  private var mViewModel: ShareBookContract.ViewModel? = null
  private lateinit var mBaseSchedulerProvider: BaseSchedulerProvider

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: ShareBookContract.ViewModel) {
    mViewModel = viewModel
  }

  override fun validateDataInput(bookRequest: BookRequest): Boolean {
    validateTitleInput(bookRequest.title)
    validateAuthorInput(bookRequest.author)
    validateDescriptionInput(bookRequest.description)
    return mValidator.validateAll(bookRequest)
  }

  override fun getData() {
    val disposable: Disposable = Single.zip(mUserRepository.getOffices(),
        mCategoryRepository.getCategory(),
        BiFunction<BaseResponse<List<Office>>, BaseResponse<List<Category>>, OfficesAndCategories>
        { listOffice, listCategory ->
          OfficesAndCategories(listCategory.items, listOffice.items)
        }).subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .doOnSubscribe { mViewModel?.onShowProgressDialog() }
        .doAfterTerminate { mViewModel?.onDismissProgressDialog() }
        .subscribe(
            { officeAndCategory ->
              mViewModel?.onGetCategorySuccess(officeAndCategory.categories)
              mViewModel?.onGetOfficeSuccess(officeAndCategory.offices)
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  override fun addBook(bookRequest: BookRequest) {
    val disposable: Disposable = mBookRepository.addBook(bookRequest)
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .doOnSubscribe { mViewModel?.onShowProgressDialog() }
        .doAfterTerminate { mViewModel?.onDismissProgressDialog() }
        .subscribe(
            { response ->
              mViewModel?.onAddBookSuccess(response.item)
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  override fun searchBookFromInternal(keyword: String?, field: String?) {
    val disposable: Disposable = mBookRepository.searchBook(keyword, field)
        .subscribeOn(mBaseSchedulerProvider.io())
        .doOnSubscribe { mViewModel?.onShowProgressDialog() }
        .doAfterTerminate { mViewModel?.onDismissProgressDialog() }
        .observeOn(mBaseSchedulerProvider.ui())
        .subscribe(
            { listBook -> mViewModel?.onSearchBookFromInternalSuccess(listBook.items?.data) },
            { error -> mViewModel?.onError(error as BaseException) })
    mCompositeDisposable.add(disposable)
  }

  override fun searchBookFromGoogleBook(title: String?) {
    val disposable: Disposable = mBookRepository.searchBookWithGoogleApi(title)
        .subscribeOn(mBaseSchedulerProvider.io())
        .doOnSubscribe { mViewModel?.onShowProgressDialog() }
        .doAfterTerminate { mViewModel?.onDismissProgressDialog() }
        .observeOn(mBaseSchedulerProvider.ui())
        .subscribe(
            { bookResponse -> mViewModel?.onSearchBookFromGoogleBookSuccess(bookResponse.items) },
            { error -> mViewModel?.onError(error as BaseException) }
        )
    mCompositeDisposable.add(disposable)
  }

  private fun validateTitleInput(title: String?) {
    var message: String? = mValidator.validateValueNonEmpty(title)
    if (!StringUtils.isBlank(message)) {
      mViewModel?.onInputTitleError(message)
    }
  }

  private fun validateAuthorInput(author: String?) {
    var message: String? = mValidator.validateValueNonEmpty(author)
    if (!StringUtils.isBlank(message)) {
      mViewModel?.onInputAuthorError(message)
    }
  }

  private fun validateDescriptionInput(description: String?) {
    var message: String? = mValidator.validateValueNonEmpty(description)
    if (!StringUtils.isBlank(message)) {
      mViewModel?.onInputDescriptionError(message)
    }
  }

  fun setSchedulerProvider(baseSchedulerProvider: BaseSchedulerProvider) {
    mBaseSchedulerProvider = baseSchedulerProvider
  }
}
