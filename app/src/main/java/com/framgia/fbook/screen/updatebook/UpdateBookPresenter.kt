package com.framgia.fbook.screen.updatebook;

import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.model.OfficesAndCategories
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.CategoryRepository
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.EditBookRequest
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.utils.common.StringUtils
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.framgia.fbook.utils.validator.Validator
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

/**
 * Listens to user actions from the UI ({@link UpdateBookActivity}), retrieves the data and updates
 * the UI as required.
 */
class UpdateBookPresenter(private val mValidator: Validator,
    private val mUserRepository: UserRepository,
    private val mCategoryRepository: CategoryRepository,
    private var mBookRepository: BookRepository) : UpdateBookContract.Presenter {

  private var mViewModel: UpdateBookContract.ViewModel? = null
  private lateinit var mBaseSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: UpdateBookContract.ViewModel) {
    mViewModel = viewModel
  }

  override fun validateDataInput(editBookRequest: EditBookRequest): Boolean {
    validateTitleInput(editBookRequest.title)
    validatorAuthor(editBookRequest.author)
    validatorDescription(editBookRequest.description)
    return mValidator.validateAll(editBookRequest)
  }

  override fun validateTitleInput(title: String?) {
    var message: String? = mValidator.validateValueNonEmpty(title)
    if (!StringUtils.isBlank(message)) {
      mViewModel?.onInputTitleError(message)
    }
  }

  override fun validatorAuthor(author: String?) {
    var message: String? = mValidator.validateValueNonEmpty(author)
    if (!StringUtils.isBlank(message)) {
      mViewModel?.onInputAuthorError(message)
    }
  }

  override fun validatorDescription(description: String?) {
    var message: String? = mValidator.validateValueNonEmpty(description)
    if (!StringUtils.isBlank(message)) {
      mViewModel?.onInputDescriptionError(message)
    }
  }

  override fun getData() {
    val disposable: Disposable = Single.zip(mUserRepository.getOffices(),
        mCategoryRepository.getCategory(),
        BiFunction<BaseResponse<List<Office>>, BaseResponse<List<Category>>, OfficesAndCategories> { listOffice, listCategory ->
          OfficesAndCategories(listCategory.items, listOffice.items)
        }).subscribeOn(
        mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .doOnSubscribe { mViewModel?.onShowProgressDialog() }
        .doAfterTerminate { mViewModel?.onHideProgressDialog() }
        .subscribe({ officeAndCategory ->
          mViewModel?.onGetCategorySuccess(officeAndCategory.categories)
          mViewModel?.onGetOfficeSuccess(officeAndCategory.offices)
        }, { error -> mViewModel?.onError(error as BaseException) })
    mCompositeDisposable.addAll(disposable)
  }

  override fun sendFormEditBook(bookId: Int?, editBookRequest: EditBookRequest) {
    val disposable: Disposable = mBookRepository.requestFormEditBook(bookId, editBookRequest)
        .subscribeOn(mBaseSchedulerProvider.io())
        .doOnSubscribe { mViewModel?.onShowProgressDialog() }
        .doAfterTerminate { mViewModel?.onHideProgressDialog() }
        .observeOn(mBaseSchedulerProvider.ui())
        .subscribe({
          mViewModel?.onRequestFormEditBookSuccess()
        }, { error -> mViewModel?.onError(error as BaseException) })
    mCompositeDisposable.addAll(disposable)
  }

  fun setBaseSchedulerProvider(baseSchedulerProvider: BaseSchedulerProvider) {
    mBaseSchedulerProvider = baseSchedulerProvider
  }
}
