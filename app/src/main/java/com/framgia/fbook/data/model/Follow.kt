package com.framgia.fbook.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by levutantuan on 9/29/17.
 */
class Follow() : BaseModel(), Parcelable {

  @Expose
  @SerializedName("followedBy")
  var followedBy: List<ListFollow>? = ArrayList()
  @Expose
  @SerializedName("following")
  var following: List<ListFollow>? = ArrayList()
  @Expose
  @SerializedName("isFollow")
  var isFollow: Boolean = false
  @Expose
  @SerializedName("countFollowed")
  var countFollowed: Int? = 0
  @Expose
  @SerializedName("countFollowing")
  var countFollowing: Int? = 0

  constructor(parcel: Parcel) : this() {
    isFollow = parcel.readValue(Boolean::class.java.classLoader) as Boolean
    countFollowed = parcel.readValue(Int::class.java.classLoader) as? Int
    countFollowing = parcel.readValue(Int::class.java.classLoader) as? Int
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeValue(followedBy)
    parcel.writeValue(following)
    parcel.writeValue(isFollow)
    parcel.writeValue(countFollowed)
    parcel.writeValue(countFollowing)
  }

  override fun describeContents(): Int {
    return 0
  }

  class ListFollow() : BaseModel(), Parcelable {
    @Expose
    @SerializedName("id")
    var id: Int? = 0
    @Expose
    @SerializedName("name")
    var name: String? = null

    constructor(parcel: Parcel) : this() {
      id = parcel.readValue(Int::class.java.classLoader) as? Int
      name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
      parcel.writeValue(id)
      parcel.writeString(name)
    }

    override fun describeContents(): Int {
      return 0
    }

    companion object CREATOR : Parcelable.Creator<ListFollow> {
      override fun createFromParcel(parcel: Parcel): ListFollow {
        return ListFollow(parcel)
      }

      override fun newArray(size: Int): Array<ListFollow?> {
        return arrayOfNulls(size)
      }
    }
  }

  companion object CREATOR : Parcelable.Creator<Follow> {
    override fun createFromParcel(parcel: Parcel): Follow {
      return Follow(parcel)
    }

    override fun newArray(size: Int): Array<Follow?> {
      return arrayOfNulls(size)
    }
  }
}
