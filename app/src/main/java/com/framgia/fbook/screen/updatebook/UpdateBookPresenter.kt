package com.framgia.fbook.screen.updatebook;

import com.framgia.fbook.data.source.remote.api.request.BookRequest
import com.framgia.fbook.utils.common.StringUtils
import com.framgia.fbook.utils.validator.Validator
import io.reactivex.disposables.CompositeDisposable

/**
 * Listens to user actions from the UI ({@link UpdateBookActivity}), retrieves the data and updates
 * the UI as required.
 */
class UpdateBookPresenter(private val mValidator: Validator) : UpdateBookContract.Presenter {

  private var mViewModel: UpdateBookContract.ViewModel? = null
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: UpdateBookContract.ViewModel) {
    mViewModel = viewModel
  }

  override fun validateDataInput(bookRequest: BookRequest): Boolean {
    validateTitleInput(bookRequest.title)
    validatorAuthor(bookRequest.author)
    validatorDescription(bookRequest.description)
    return mValidator.validateAll(bookRequest)
  }

  override fun validateTitleInput(title: String?) {
    val message: String? = mValidator.validateValueNonEmpty(title)
    if (!StringUtils.isBlank(title)) {
      mViewModel?.onInputTitleError(message)
    }
  }

  override fun validatorAuthor(author: String?) {
    val message: String? = mValidator.validateValueNonEmpty(author)
    if (!StringUtils.isBlank(author)) {
      mViewModel?.onInputAuthorError(message)
    }
  }

  override fun validatorDescription(description: String?) {
    val message: String? = mValidator.validateValueNonEmpty(description)
    if (!StringUtils.isBlank(description)) {
      mViewModel?.onInputDescriptionError(message)
    }
  }
}
