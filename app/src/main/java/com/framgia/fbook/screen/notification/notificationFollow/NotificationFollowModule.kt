package com.framgia.fbook.screen.notification.notificationFollow

import android.support.v4.app.Fragment
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.screen.notification.NotificationAdapter
import com.framgia.fbook.utils.dagger.FragmentScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the [NotificationFollowPresenter].
 */
@Module
class NotificationFollowModule(private val mFragment: Fragment) {


  @FragmentScope
  @Provides
  fun providePresenter(userRepository: UserRepository,
      schedulerProvider: BaseSchedulerProvider): NotificationFollowContract.Presenter {
    val presenter = NotificationFollowPresenter(userRepository)
    presenter.setViewModel(mFragment as NotificationFollowContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }

  @FragmentScope
  @Provides
  fun provideNotificationAdapter(): NotificationAdapter {
    return NotificationAdapter(mFragment.context)
  }

  @FragmentScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mFragment)
  }

  @FragmentScope
  @Provides
  fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource,
      userLocalDataSource: UserLocalDataSource): UserRepository {
    return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
  }

  @FragmentScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mFragment.context)
  }
}
