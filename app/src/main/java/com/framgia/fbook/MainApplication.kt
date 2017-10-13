package com.framgia.fbook

import android.app.Application
import android.support.multidex.MultiDex
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.framgia.fbook.data.source.remote.api.NetworkModule
import com.pusher.android.PusherAndroid
import com.pusher.android.PusherAndroidOptions
import com.pusher.android.notifications.PushNotificationRegistration
import com.pusher.android.notifications.interests.InterestSubscriptionChangeListener
import com.pusher.android.notifications.tokens.PushNotificationRegistrationListener
import io.fabric.sdk.android.Fabric


/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainApplication : Application() {

  lateinit var appComponent: AppComponent

  private val TAG = MainApplication::javaClass.name

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent.builder()
        .applicationModule(ApplicationModule(applicationContext))
        .networkModule(NetworkModule(this))
        .build()
    Fabric.with(this, Crashlytics())
    MultiDex.install(this)
    configPusher()
  }

  private fun configPusher() {

    val pusherOptions = PusherAndroidOptions()
    pusherOptions.setCluster(BuildConfig.CLUSTER_KEY)

    val pusher = PusherAndroid(BuildConfig.APP_KEY, pusherOptions)
    val nativePusher: PushNotificationRegistration = pusher.nativePusher()

    registerPusher(nativePusher)
  }

  private fun registerPusher(nativePusher: PushNotificationRegistration?) {
    nativePusher?.registerFCM(this, object : PushNotificationRegistrationListener {
      override fun onSuccessfulRegistration() {
        Log.d(TAG, "Registration Successful !")
        subscribePusher(nativePusher)
      }

      override fun onFailedRegistration(statusCode: Int, response: String) {
        Log.d(TAG, "Registration failed with code $statusCode $response")
      }
    })
  }

  private fun subscribePusher(nativePusher: PushNotificationRegistration?) {
    nativePusher?.subscribe(BuildConfig.CHANNEL_KEY, object : InterestSubscriptionChangeListener {
      override fun onSubscriptionChangeSucceeded() {
        Log.d(TAG, "Subscribe Successful !")
        listenerPusher(nativePusher)
      }

      override fun onSubscriptionChangeFailed(statusCode: Int, response: String) {
        Log.d(TAG, "Subscribe failed with code $statusCode $response")
      }
    })
  }

  private fun listenerPusher(nativePusher: PushNotificationRegistration?) {
    nativePusher?.setFCMListener { remoteMessage ->
      remoteMessage?.let {
        //TODO edit later
        val message = remoteMessage.notification.body
        Log.d(TAG, "RemoteMessage: " + remoteMessage.from)
        Log.d(TAG, "Message: " + message)
      }
    }
  }
}
