package com.framgia.fbook.fireBase

import com.framgia.fbook.MainApplication
import com.framgia.fbook.data.source.UserRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

/**
 * Created by framgia on 11/10/2017.
 */
class FireBaseMessageService : FirebaseMessagingService() {

  private val TAG = FireBaseMessageService::javaClass.name

  @Inject
  internal lateinit var mUserRepository: UserRepository

  override fun onCreate() {
    super.onCreate()

    DaggerFireBaseComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .fireBaseModule(FireBaseModule(this))
        .build()
        .inject(this)
  }

  override fun onMessageReceived(remoteMessage: RemoteMessage?) {
    super.onMessageReceived(remoteMessage)
    //TODO edit later
  }
}
