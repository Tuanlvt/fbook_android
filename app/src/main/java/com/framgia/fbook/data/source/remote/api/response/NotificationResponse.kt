package com.framgia.fbook.data.source.remote.api.response

import com.framgia.fbook.data.model.Notification
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Hyperion on 04/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class NotificationResponse : BaseRespone() {
  @SerializedName("notification")
  @Expose
  var notificationUser: Notification? = null
  @SerializedName("notificationFollow")
  @Expose
  var notificationFollow: Notification? = null
}
