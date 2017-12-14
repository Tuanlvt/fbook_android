package com.framgia.fbook.data.source.remote.api.request

import com.framgia.fbook.R
import com.framgia.fbook.utils.validator.Rule
import com.framgia.fbook.utils.validator.ValidType
import com.framgia.fbook.utils.validator.Validation
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Tuanlvt on 13/12/2017.
 */
class EditBookRequest : BaseRequest() {

  @Validation(
      Rule(types = intArrayOf(ValidType.NON_EMPTY), message = R.string.is_empty)
  )
  @Expose
  @SerializedName("title")
  var title: String? = ""
  @Validation(
      Rule(types = intArrayOf(ValidType.NON_EMPTY), message = R.string.is_empty)
  )
  @Expose
  @SerializedName("description")
  var description: String? = ""
  @Validation(
      Rule(types = intArrayOf(ValidType.NON_EMPTY), message = R.string.is_empty)
  )
  @Expose
  @SerializedName("author")
  var author: String? = ""
  @Expose
  @SerializedName("publish_date")
  var publishDate: String? = ""
  @Expose
  @SerializedName("category_id")
  var categoryId: Int? = null
  @Expose
  @SerializedName("office_id")
  var officeId: Int? = null
  @Expose
  @SerializedName("code")
  var codeBook: String? = ""
  @Expose
  @SerializedName("medias")
  var medias: List<Media>? = arrayListOf()
  @Expose
  @SerializedName("update")
  var update: List<Update> = arrayListOf()

  class Update : BaseRequest() {
    @Expose
    @SerializedName("fileimage.jpg")
    var fileImage: String? = null
    @Expose
    @SerializedName("id")
    var id: Int? = null
    @Expose
    @SerializedName("type")
    var type: Int? = null
  }

  class Media : BaseRequest() {
    @Expose
    @SerializedName("fileimage.jpg")
    var imageFile: String? = null
    @Expose
    @SerializedName("type")
    var type: Int? = null
  }
}
