package com.framgia.fbook.data.source.remote.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by levutantuan on 10/3/17.
 */
class FollowOrUnFollowUserRequest : BaseRequest() {
  @Expose
  @SerializedName("item")
  var item: Item? = null

  class Item : BaseRequest() {
    @Expose
    @SerializedName("user_id")
    var userId: Int? = 0
  }
}

