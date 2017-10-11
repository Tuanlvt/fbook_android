package com.framgia.fbook.fireBase

import com.framgia.fbook.AppComponent
import com.framgia.fbook.utils.dagger.ServiceScope
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.Component

/**
 * Created by framgia on 11/10/2017.
 */

@ServiceScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(FireBaseModule::class))
interface FireBaseComponent {
  fun inject(fireBaseMessagingService: FirebaseMessagingService)
}
