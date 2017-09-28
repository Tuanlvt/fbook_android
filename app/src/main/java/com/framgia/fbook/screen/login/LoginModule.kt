package com.framgia.fbook.screen.login;

import android.app.Activity
import android.content.Context
import com.framgia.fbook.data.source.TokenRepository
import com.framgia.fbook.data.source.TokenRepositoryImpl
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.TokenLocalDataSource
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.utils.dagger.ActivityScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.framgia.fbook.utils.validator.Validator
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link LoginPresenter}.
 */
@Module
class LoginModule(private val mActivity: Activity) {

  @ActivityScope
  @Provides
  fun providePresenter(schedulerProvider: BaseSchedulerProvider,
      userRepository: UserRepository, tokenRepository: TokenRepository,
      validator: Validator): LoginContract.Presenter {
    val presenter = LoginPresenter(userRepository, tokenRepository, validator)
    presenter.setViewModel(mActivity as LoginContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }

  @ActivityScope
  @Provides
  fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource,
      userLocalDataSource: UserLocalDataSource): UserRepository {
    return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
  }

  @ActivityScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mActivity)
  }

  @ActivityScope
  @Provides
  fun provideValidator(context: Context): Validator {
    return Validator(context, LoginActivity::class.java)
  }

  @ActivityScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mActivity)
  }
}
