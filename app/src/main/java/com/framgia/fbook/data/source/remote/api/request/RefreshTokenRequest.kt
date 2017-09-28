package com.framgia.fbook.data.source.remote.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by framgia on 27/09/2017.
 */
class RefreshTokenRequest : BaseRequest() {
  @SerializedName("refresh_token")
  @Expose
  var refresh_token: String? = null
}
