package com.framgia.fbook.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class User() : BaseModel(), Parcelable {

  @SerializedName("id")
  @Expose
  var id: Int? = null
  @SerializedName("name")
  @Expose
  var name: String? = null
  @SerializedName("email")
  @Expose
  var email: String? = null
  @SerializedName("phone")
  @Expose
  var phone: String? = null
  @SerializedName("code")
  @Expose
  var code: String? = null
  @SerializedName("position")
  @Expose
  var position: String? = null
  @SerializedName("role")
  @Expose
  var role: String? = null
  @SerializedName("office_id")
  @Expose
  var officeId: Int? = null
  @SerializedName("created_at")
  @Expose
  var createdAt: String? = null
  @SerializedName("updated_at")
  @Expose
  var updatedAt: String? = null
  @SerializedName("deleted_at")
  @Expose
  var deletedAt: String? = null
  @SerializedName("avatar")
  @Expose
  var avatar: String? = null
  @SerializedName("tags")
  @Expose
  var tag: String? = null
  @SerializedName("favorite_categories")
  @Expose
  var categories: List<Category>? = ArrayList()
  @SerializedName("owner_id")
  @Expose
  var ownerId: Int? = null
  @SerializedName("pivot")
  @Expose
  var pivot: Pivot? = null
  var owner: Owner? = null

  constructor(parcel: Parcel) : this() {
    id = parcel.readValue(Int::class.java.classLoader) as? Int
    name = parcel.readString()
    email = parcel.readString()
    phone = parcel.readString()
    code = parcel.readString()
    position = parcel.readString()
    role = parcel.readString()
    officeId = parcel.readValue(Int::class.java.classLoader) as? Int
    createdAt = parcel.readString()
    updatedAt = parcel.readString()
    deletedAt = parcel.readString()
    avatar = parcel.readString()
    tag = parcel.readString()
    categories = parcel.createTypedArrayList(Category)
    ownerId = parcel.readValue(Int::class.java.classLoader) as? Int
    pivot = parcel.readParcelable(Pivot::class.java.classLoader)
  }

  class Pivot() : BaseModel(), Parcelable {
    @SerializedName("book_id")
    @Expose
    var bookId: Int? = null
    @SerializedName("user_id")
    @Expose
    var userId: Int? = null
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("owner_id")
    @Expose
    var ownerId: Int? = null

    constructor(parcel: Parcel) : this() {
      bookId = parcel.readValue(Int::class.java.classLoader) as? Int
      userId = parcel.readValue(Int::class.java.classLoader) as? Int
      status = parcel.readValue(Int::class.java.classLoader) as? Int
      ownerId = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
      parcel.writeValue(bookId)
      parcel.writeValue(userId)
      parcel.writeValue(status)
      parcel.writeValue(ownerId)
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
    parcel.writeValue(id)
    parcel.writeString(name)
    parcel.writeString(email)
    parcel.writeString(phone)
    parcel.writeString(code)
    parcel.writeString(position)
    parcel.writeString(role)
    parcel.writeValue(officeId)
    parcel.writeString(createdAt)
    parcel.writeString(updatedAt)
    parcel.writeString(deletedAt)
    parcel.writeString(avatar)
    parcel.writeString(tag)
    parcel.writeTypedList(categories)
    parcel.writeValue(ownerId)
    parcel.writeParcelable(pivot, flags)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<User> {
    override fun createFromParcel(parcel: Parcel): User {
      return User(parcel)
    }

    override fun newArray(size: Int): Array<User?> {
      return arrayOfNulls(size)
    }
  }
}
