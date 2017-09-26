package com.framgia.fbook.data.source.remote.api.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by framgia on 26/09/2017.
 */
class ReviewBookRequest : BaseRequest() {
  @Expose
  @SerializedName("item")
  var reviewBook = ReviewBook()

  class ReviewBook {
    @Expose
    @SerializedName("content")
    var content: String? = null
    @Expose
    @SerializedName("star")
    var star: Float? = null
  }
}
