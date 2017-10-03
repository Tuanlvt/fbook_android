package com.framgia.fbook.screen.profile

import com.framgia.fbook.data.model.Follow
import com.framgia.fbook.data.model.User

/**
 * Created by levutantuan on 9/28/17.
 */
interface GetUserListener {
  interface onGetUserPersonal {
    fun onGetUser(user: User?)
  }

  interface onGetUserCategory {
    fun onGetUser(user: User?)
  }

  interface onGetFollowUser {
    fun onGetFollow(follow: Follow?)
  }
}
