package com.framgia.fbook.screen.login;

import android.util.Log
import com.framgia.fbook.data.source.TokenRepository
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.screen.BaseViewModel
import com.framgia.fbook.utils.common.StringUtils
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.framgia.fbook.utils.validator.Validator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
class LoginPresenter(private val mUserRepository: UserRepository,
    private val mTokenRepository: TokenRepository,
    private val mValidator: Validator) : LoginContract.Presenter {

  companion object {
    private val TAG = LoginPresenter::class.java.name
  }

  private var mViewModel: LoginContract.ViewModel? = null
  private lateinit var mSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {
  }

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: LoginContract.ViewModel) {
    mViewModel = viewModel
  }

  override fun checkUserLogin() {
    if (StringUtils.isBlank(mTokenRepository.getToken()?.accessToken)) {
      return
    }
    mViewModel?.onUserLoggedIn()
  }

  override fun validateDataInput(email: String?, password: String?): Boolean {
    validateEmailInput(email)
    validatePasswordInput(password)
    try {
      return mValidator.validateAll<BaseViewModel>(mViewModel!!)
    } catch (e: IllegalAccessException) {
      Log.e(TAG, "validateDataInput: ", e)
      return false
    }
  }

  override fun validateEmailInput(email: String?) {
    var message: String? = mValidator.validateValueNonEmpty(email)
    if (StringUtils.isBlank(message)) {
      message = mValidator.validateEmailFormat(email)
    }
    mViewModel?.onInvalidEmail(message)
  }

  override fun validatePasswordInput(password: String?) {
    var message: String? = mValidator.validateValueNonEmpty(password)
    if (StringUtils.isBlank(message)) {
      message = mValidator.validateValueRangeMin6(password)
    }
    mViewModel?.onInvalidPassWord(message)
  }

  override fun login(email: String?, password: String?) {
    val disposable: Disposable = mUserRepository.login(email, password)
        .subscribeOn(mSchedulerProvider.io())
        .doOnSubscribe { mViewModel?.onShowProgressDialog() }
        .doAfterTerminate { mViewModel?.onDismissProgressDialog() }
        .flatMap({ tokenResponse ->
          mTokenRepository.saveToken(tokenResponse.token)
          mUserRepository.getUser()
        })
        .observeOn(mSchedulerProvider.ui())
        .subscribe({ user ->
          mUserRepository.saveUser(user.item)
          mViewModel?.onUserLoggedIn()
        }, { e -> mViewModel?.onError(e as BaseException) })
    mCompositeDisposable.add(disposable)
  }

  fun setSchedulerProvider(schedulerProvider: BaseSchedulerProvider) {
    mSchedulerProvider = schedulerProvider
  }
}
