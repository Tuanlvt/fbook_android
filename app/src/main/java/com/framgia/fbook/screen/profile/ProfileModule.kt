package com.framgia.fbook.screen.profile;

import android.app.Activity
import android.support.v4.app.FragmentActivity
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.utils.dagger.ActivityScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ProfilePresenter}.
 */
@Module
class ProfileModule(private val mActivity: Activity) {

  @ActivityScope
  @Provides
  fun providePresenter(userRepository: UserRepository,
      schedulerProvider: BaseSchedulerProvider): ProfileContract.Presenter {
    val presenter = ProfilePresenter(userRepository)
    presenter.setSchedulerProvider(schedulerProvider)
    presenter.setViewModel(mActivity as ProfileContract.ViewModel)
    return presenter
  }

  @ActivityScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mActivity)
  }

  @ActivityScope
  @Provides
  fun provideProfileAdapter(): ProfileAdapter {
    return ProfileAdapter(mActivity,
        (mActivity as FragmentActivity).supportFragmentManager)
  }

  @ActivityScope
  @Provides
  fun providerUserRepository(userRemoteDataSource: UserRemoteDataSource,
      userLocalDataSource: UserLocalDataSource): UserRepository {
    return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
  }

  @ActivityScope
  @Provides
  fun providerDialogManager(): DialogManager {
    return DialogManagerImpl(mActivity)
  }
}
