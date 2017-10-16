package com.framgia.fbook.screen.login;

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.View
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.ActivityLoginBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.validator.Rule
import com.framgia.fbook.utils.validator.ValidType
import com.framgia.fbook.utils.validator.Validation
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * Login Screen.
 */
open class LoginActivity : BaseActivity(), LoginContract.ViewModel {

  companion object {
    val TAG: String = LoginActivity::class.java.name
  }

  @Inject
  internal lateinit var mPresenter: LoginContract.Presenter
  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mDialogManager: DialogManager
  private lateinit var mBinding: ActivityLoginBinding
  @Validation(
      Rule(types = intArrayOf(ValidType.EMAIL_FORMAT), message = R.string.invalid_email_format),
      Rule(types = intArrayOf(ValidType.NON_EMPTY), message = R.string.is_empty)
  )
  var email: ObservableField<String> = ObservableField()
  @Validation(
      Rule(types = intArrayOf(ValidType.NON_EMPTY), message = R.string.is_empty),
      Rule(types = intArrayOf(ValidType.VALUE_RANGE_MIN_6), message = R.string.least_6_characters)
  )
  var password: ObservableField<String> = ObservableField()
  var emailErrorMsg: ObservableField<String> = ObservableField()
  var passwordErrorMsg: ObservableField<String> = ObservableField()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerLoginComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .loginModule(LoginModule(this))
        .build()
        .inject(this)

    mBinding = DataBindingUtil.setContentView<ActivityLoginBinding>(this,
        R.layout.activity_login)
    mBinding.viewModel = this
    mPresenter.checkUserLogin()
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onInvalidEmail(errorMsg: String?) {
    emailErrorMsg.set(errorMsg)
  }

  override fun onInvalidPassWord(errorMsg: String?) {
    passwordErrorMsg.set(errorMsg)
  }

  override fun onError(exception: BaseException) {
    mDialogManager.showSnackBarTitleString(mBinding.imageLogo, exception.getMessageError())
  }

  override fun onUserLoggedIn() {
    mNavigator.finishActivityWithResult(Activity.RESULT_OK)
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  fun onClickLogin() {
    if (!mPresenter.validateDataInput(email.get(), password.get())) {
      return
    }
    mPresenter.login(email.get(), password.get())
  }

  fun ForgotPasswordClick(view: View) {
    mDialogManager.showSnackBarNoActionBar(mBinding.imageLogo,
        R.string.this_feature_will_be_update_soon)
  }
}
