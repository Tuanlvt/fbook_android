package com.framgia.fbook.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Hyperion on 9/7/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class GoogleBook() : BaseModel(), Parcelable {
  @SerializedName("id")
  @Expose
  var id: String? = null
  @SerializedName("volumeInfo")
  @Expose
  var volumeInfo: VolumeInfo? = null

  constructor(parcel: Parcel) : this() {
    id = parcel.readString()
  }

  inner class VolumeInfo : BaseModel() {
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("subtitle")
    @Expose
    var subtitle: String? = null
    @SerializedName("authors")
    @Expose
    var listAuthor: List<String>? = null
    @SerializedName("publisher")
    @Expose
    var publisher: String? = null
    @SerializedName("publishedDate")
    @Expose
    var publishedDate: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("averageRating")
    @Expose
    var averageRating: Float? = null
    @SerializedName("imageLinks")
    @Expose
    var imageLink: ImageLink? = null
  }

  inner class ImageLink : BaseModel() {
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String? = null
    @SerializedName("medium")
    @Expose
    var medium: String? = null
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(id)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<GoogleBook> {
    override fun createFromParcel(parcel: Parcel): GoogleBook {
      return GoogleBook(parcel)
    }

    override fun newArray(size: Int): Array<GoogleBook?> {
      return arrayOfNulls(size)
    }
  }
}
