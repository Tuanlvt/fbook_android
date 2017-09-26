package com.framgia.fbook.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by nguyenhuy95dn on 8/28/2017.
 */

class Owner() : BaseModel(), Parcelable {
  @Expose
  @SerializedName("id")
  var id: Int = 0
  @Expose
  @SerializedName("name")
  var name: String? = null
  @Expose
  @SerializedName("avatar")
  var avatar: String? = null
  @Expose
  @SerializedName("position")
  var position: String? = null
  @Expose
  @SerializedName("pivot")
  var pivot: Pivot? = null

  constructor(parcel: Parcel) : this() {
    id = parcel.readInt()
    name = parcel.readString()
    avatar = parcel.readString()
    position = parcel.readString()
    pivot = parcel.readParcelable(Pivot::class.java.classLoader)
  }

  class Pivot() : BaseModel(), Parcelable {
    @Expose
    @SerializedName("book_id")
    var bookId: Int = 0
    @Expose
    @SerializedName("user_id")
    var userId: Int = 0
    @Expose
    @SerializedName("status")
    var status: String? = null

    constructor(parcel: Parcel) : this() {
      bookId = parcel.readInt()
      userId = parcel.readInt()
      status = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
      parcel.writeInt(bookId)
      parcel.writeInt(userId)
      parcel.writeString(status)
    }

    override fun describeContents(): Int {
      return 0
    }

    companion object CREATOR : Parcelable.Creator<Pivot> {
      override fun createFromParcel(parcel: Parcel): Pivot {
        return Pivot(parcel)
      }

      override fun newArray(size: Int): Array<Pivot?> {
        return arrayOfNulls(size)
      }
    }
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeInt(id)
    parcel.writeString(name)
    parcel.writeString(avatar)
    parcel.writeString(position)
    parcel.writeParcelable(pivot, flags)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Owner> {
    override fun createFromParcel(parcel: Parcel): Owner {
      return Owner(parcel)
    }

    override fun newArray(size: Int): Array<Owner?> {
      return arrayOfNulls(size)
    }
  }
}
