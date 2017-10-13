package com.framgia.fbook.screen.main

import com.framgia.fbook.data.model.Notification

/**
 * Created by Hyperion on 06/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
interface NotificationListener {
  fun getNotificationFollow(notification: Notification?)

  fun onUpdateNotificationSuccess()

  interface UserListener {
    fun getEnable(enable: Boolean)
  }
}
