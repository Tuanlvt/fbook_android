package com.framgia.fbook.utils

import com.framgia.fbook.utils.common.StringUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object RetrofitUtils {

  fun toRequestBody(value: String): RequestBody? {
    return if (StringUtils.isBlank(value)) {
      null
    } else RequestBody.create(
        MediaType.parse("text/plain"), value)
  }

  fun toMutilPartBody(position: Int?, file: File?): MultipartBody.Part? {
    if (file == null) {
      return null
    }
    val requestFileAvatar = RequestBody.create(MediaType.parse("multipart/form-data"), file)
    return MultipartBody.Part.createFormData("medias[$position][file]", file.name,
        requestFileAvatar)
  }
}
