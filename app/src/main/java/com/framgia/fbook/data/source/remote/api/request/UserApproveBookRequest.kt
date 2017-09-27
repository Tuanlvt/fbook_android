package com.framgia.fbook.data.source.remote.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Hyperion on 27/09/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class UserApproveBookRequest : BaseRequest() {

  @SerializedName("key")
  @Expose
  val key = "approve"
  @SerializedName("user_id")
  @Expose
  var userId: String? = null
}
