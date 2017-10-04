package com.framgia.fbook.data.source.remote.api.response

import android.app.Notification
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Hyperion on 04/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class NotificationResponse : BaseRespone() {
  @SerializedName("from")
  @Expose
  var from: Int? = null
  @SerializedName("current_page")
  @Expose
  var currentPage: Int? = null
  @SerializedName("last_page")
  @Expose
  var lastPage: Int? = null
  @SerializedName("next_page_url")
  @Expose
  var nextPageUrl: String? = null
  @SerializedName("path")
  @Expose
  var path: String? = null
  @SerializedName("per_page")
  @Expose
  var perPage: Int? = null
  @SerializedName("prev_page_url")
  @Expose
  var pervPageUrl: String? = null
  @SerializedName("to")
  @Expose
  var to: Int? = null
  @SerializedName("total")
  @Expose
  var total: Int? = null
  @SerializedName("data")
  @Expose
  var listNotification: Notification? = null
}
