package com.framgia.fbook.screen.notification.notificationFollow

import android.support.v4.app.Fragment
import com.framgia.fbook.utils.dagger.FragmentScope
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
  fun providePresenter(): NotificationFollowContract.Presenter {
    val presenter = NotificationFollowPresenter()
    presenter.setViewModel(mFragment as NotificationFollowContract.ViewModel)
    return presenter
  }
}
