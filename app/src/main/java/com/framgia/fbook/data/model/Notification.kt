package com.framgia.fbook.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Hyperion on 04/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class Notification() : BaseModel(), Parcelable {

  @SerializedName("id")
  @Expose
  var id: Int? = null
  @SerializedName("user_send_id")
  @Expose
  var userSendId: Int? = null
  @SerializedName("user_receive_id")
  var uSerReceiveId: Int? = null
  @SerializedName("target_id")
  @Expose
  var targetId: Int? = null
  @SerializedName("viewed")
  @Expose
  var viewed: Int? = null
  @SerializedName("type")
  @Expose
  var type: String? = null
  @SerializedName("created_at")
  @Expose
  var createdAt: String? = null
  @SerializedName("updated_at")
  @Expose
  var updateAt: String? = null
  @SerializedName("book")
  @Expose
  var book: Book? = null
  @SerializedName("user_send")
  @Expose
  var user: User? = null

  constructor(parcel: Parcel) : this() {
    id = parcel.readValue(Int::class.java.classLoader) as? Int
    userSendId = parcel.readValue(Int::class.java.classLoader) as? Int
    uSerReceiveId = parcel.readValue(Int::class.java.classLoader) as? Int
    targetId = parcel.readValue(Int::class.java.classLoader) as? Int
    viewed = parcel.readValue(Int::class.java.classLoader) as? Int
    type = parcel.readString()
    createdAt = parcel.readString()
    updateAt = parcel.readString()
    book = parcel.readParcelable(Book::class.java.classLoader)
    user = parcel.readParcelable(User::class.java.classLoader)
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeValue(id)
    parcel.writeValue(userSendId)
    parcel.writeValue(uSerReceiveId)
    parcel.writeValue(targetId)
    parcel.writeValue(viewed)
    parcel.writeString(type)
    parcel.writeString(createdAt)
    parcel.writeString(updateAt)
    parcel.writeParcelable(book, flags)
    parcel.writeParcelable(user, flags)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Notification> {
    override fun createFromParcel(parcel: Parcel): Notification {
      return Notification(parcel)
    }

    override fun newArray(size: Int): Array<Notification?> {
      return arrayOfNulls(size)
    }
  }

}
