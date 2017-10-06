package com.framgia.fbook.screen.notification.notificationUser

import com.framgia.fbook.screen.main.MainComponent
import com.framgia.fbook.utils.dagger.FragmentScope
import dagger.Component

/**
 * This is a Dagger component. Refer to [com.framgia.fbook.screen.MainApplication] for the
 * list of Dagger components
 * used in this application.
 */
@FragmentScope
@Component(dependencies = arrayOf(MainComponent::class),
    modules = arrayOf(NotificationModule::class))
interface NotificationComponent {
  fun inject(notificationFragment: NotificationFragment)
}
