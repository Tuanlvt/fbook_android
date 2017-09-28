package com.framgia.fbook.screen.login

/**
 * Created by ThS on 8/31/2017.
 */
//@RunWith(MockitoJUnitRunner::class)
//class LoginPresenterTest {

//  @InjectMocks
//  lateinit var mPresenter: LoginPresenter
//  @Mock
//  lateinit var mViewModel: LoginActivity
//  @InjectMocks
//  lateinit var mBaseSchedulerProvider: ImmediateSchedulerProvider
//  @Mock
//  lateinit var mUserRepository: UserRepositoryImpl
//  @Mock
//  lateinit var mTokenRepository: TokenRepositoryImpl
//  @Mock
//  lateinit var mValidator: Validator
//  @Mock
//  lateinit var mDialogManager: DialogManagerImpl
//  @Mock
//  lateinit var mNavigator: Navigator
//  val email = "tran.dinh.vi@framgia.com"
//  val password = "12345678"
//
//  @Before
//  fun setUp() {
//    mPresenter.setViewModel(mViewModel as LoginContract.ViewModel)
//    mPresenter.setSchedulerProvider(mBaseSchedulerProvider)
//  }

//  @Test
//  fun login_shouldReturnToken_whenLogin() {
//    val token = SignInResponse()
//    Mockito.`when`(mUserRepository.login(email, password)).thenReturn(Single.just(token))
//    mPresenter.login(email, password)
//    Mockito.verify(mViewModel).onUserLoggedIn()
//  }
//
//  @Test(expected = BaseException::class)
//  fun login_shouldReturnError_whenLogin() {
//    val baseException = BaseException(Type.HTTP, ErrorResponse())
//    Mockito.`when`(mUserRepository.login(email, password)).thenThrow(baseException)
//    mPresenter.login(email, password)
//    Mockito.verify(mViewModel).onError(baseException)
//  }
//
//  @Test
//  fun validateEmailInput_shouldShowNotError_whenValidate() {
//    Mockito.`when`(mValidator.validateValueNonEmpty(email)).thenReturn("")
//    Mockito.`when`(mValidator.validateEmailFormat(email)).thenReturn("")
//    mPresenter.validateEmailInput(email)
//    Mockito.verify(mViewModel).onInvalidEmail("")
//  }
//
//  @Test
//  fun validateEmailInput_shouldShowErrorNonEmpty_whenInputNoneEmail() {
//    val errorNonEmptyMsg = "Empty, please enter!"
//    Mockito.`when`(mValidator.validateValueNonEmpty("")).thenReturn(errorNonEmptyMsg)
//    mPresenter.validateEmailInput("")
//    Mockito.verify(mViewModel).onInvalidEmail(errorNonEmptyMsg)
//  }
//
//  @Test
//  fun validateEmailInput_shouldShowError_whenInputEmailWrongFormat() {
//    val emailError = "phan.xuan.thuan"
//    val errorEmailFormatMsg = "Invalid email format!"
//    Mockito.`when`(mValidator.validateValueNonEmpty(emailError)).thenReturn(errorEmailFormatMsg)
//    mPresenter.validateEmailInput(emailError)
//    Mockito.verify(mViewModel).onInvalidEmail(errorEmailFormatMsg)
//  }
//
//  @Test
//  fun validatePasswordInput_shouldNotError_whenValidate() {
//    Mockito.`when`(mValidator.validateValueNonEmpty(password)).thenReturn("")
//    Mockito.`when`(mValidator.validateValueRangeMin6(password)).thenReturn("")
//    mPresenter.validatePasswordInput(password)
//    Mockito.verify(mViewModel).onInvalidPassWord("")
//  }
//
//  @Test
//  fun validatePasswordInput_shouldError_whenInputPassword() {
//    val errorPasswordMsg = "lease enter at least 6 characters!"
//    val passwordError = "123456"
//    Mockito.`when`(mValidator.validateValueRangeMin6(passwordError)).thenReturn(errorPasswordMsg)
//    mPresenter.validatePasswordInput(passwordError)
//    Mockito.verify(mViewModel).onInvalidPassWord(errorPasswordMsg)
//  }

//}
