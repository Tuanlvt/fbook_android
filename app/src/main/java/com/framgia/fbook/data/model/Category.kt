package com.framgia.fbook.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by levutantuan on 9/11/17.
 */
class Category() : BaseModel(), Parcelable {

  @SerializedName("id")
  @Expose
  var id: Int = 0
  @SerializedName("name")
  @Expose
  var name: String? = null
  @SerializedName("description")
  @Expose
  var description: String? = null
  var favorite: Boolean? = false

  constructor(parcel: Parcel) : this() {
    id = parcel.readInt()
    name = parcel.readString()
    description = parcel.readString()
    favorite = parcel.readInt() != 0
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeInt(id)
    parcel.writeString(name)
    parcel.writeString(description)
    parcel.writeValue(favorite)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Category> {
    override fun createFromParcel(parcel: Parcel): Category {
      return Category(parcel)
    }

    override fun newArray(size: Int): Array<Category?> {
      return arrayOfNulls(size)
    }
  }
}
