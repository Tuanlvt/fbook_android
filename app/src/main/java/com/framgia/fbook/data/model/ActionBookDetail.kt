package com.framgia.fbook.data.model

import com.framgia.fbook.data.source.remote.api.request.BaseRequest
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by framgia on 19/09/2017.
 */
class ActionBookDetail : BaseRequest() {

  @Expose
  @SerializedName("book_id")
  var bookId: Int? = null
  @Expose
  @SerializedName("owner_id")
  var ownerId: Int? = null
  @Expose
  @SerializedName("status")
  var status: Int? = null
}
