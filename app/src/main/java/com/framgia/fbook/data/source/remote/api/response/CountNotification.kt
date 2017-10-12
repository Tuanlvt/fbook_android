package com.framgia.fbook.data.source.remote.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Hyperion on 11/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class CountNotification : BaseRespone() {
  @SerializedName("count")
  @Expose
  var count: Int? = null
}
