package com.framgia.fbook.screen.main

import android.app.Activity
import android.support.v4.app.FragmentActivity
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.utils.dagger.ActivityScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.framia.fbook.screen.main.MainContract
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * Created by daolq on 8/14/17.
 */
@Module
class MainModule(private val mActivity: Activity) {


  @ActivityScope
  @Provides
  fun providePresenter(userRepository: UserRepository,
      schedulerProvider: BaseSchedulerProvider): MainContract.Presenter {
    val presenter = MainPresenter(userRepository)
    presenter.setViewModel(mActivity as MainContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }

  @ActivityScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mActivity)
  }

  @ActivityScope
  @Provides
  fun providePageAdapter(): MainContainerPagerAdapter {
    return MainContainerPagerAdapter(
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
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mActivity)
  }
}
