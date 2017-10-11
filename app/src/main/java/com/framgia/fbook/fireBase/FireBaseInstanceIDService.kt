package com.framgia.fbook.fireBase

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by framgia on 11/10/2017.
 */

class FireBaseInstanceIDService : FirebaseInstanceIdService() {

  private val TAG: String = FireBaseInstanceIDService::javaClass.name

  override fun onTokenRefresh() {
    getToken()
    Log.d(TAG, "Refreshed token: " + getToken())
  }

  public fun getToken(): String? {
    return FirebaseInstanceId.getInstance().token
  }
}
