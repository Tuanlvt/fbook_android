package com.framgia.fbook.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by nguyenhuy95dn on 8/28/2017.
 */

class Image() : Parcelable {
  @Expose
  @SerializedName("path")
  var path: String? = null
  @Expose
  @SerializedName("size")
  var size: Int = 0
  @Expose
  @SerializedName("mobile")
  var mobileImage: MobileImage? = null

  constructor(parcel: Parcel) : this() {
    path = parcel.readString()
    size = parcel.readInt()
    mobileImage = parcel.readParcelable(MobileImage::class.java.classLoader)
  }

  class MobileImage() : Parcelable {
    @Expose
    @SerializedName("thumbnail_path")
    var thumbnailPath: String? = null
    @Expose
    @SerializedName("small_path")
    var smallPath: String? = null
    @Expose
    @SerializedName("medium_path")
    var mediumPath: String? = null
    @Expose
    @SerializedName("large_path")
    var largePath: String? = null

    constructor(parcel: Parcel) : this() {
      thumbnailPath = parcel.readString()
      smallPath = parcel.readString()
      mediumPath = parcel.readString()
      largePath = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
      parcel?.writeString(thumbnailPath)
      parcel?.writeString(smallPath)
      parcel?.writeString(mediumPath)
      parcel?.writeString(largePath)
    }

    override fun describeContents(): Int {
      return 0
    }

    companion object CREATOR : Parcelable.Creator<MobileImage> {
      override fun createFromParcel(parcel: Parcel): MobileImage {
        return MobileImage(parcel)
      }

      override fun newArray(size: Int): Array<MobileImage?> {
        return arrayOfNulls(size)
      }
    }
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(path)
    parcel.writeInt(size)
    parcel.writeParcelable(mobileImage, flags)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Image> {
    override fun createFromParcel(parcel: Parcel): Image {
      return Image(parcel)
    }

    override fun newArray(size: Int): Array<Image?> {
      return arrayOfNulls(size)
    }
  }
}
