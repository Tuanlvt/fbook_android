package com.framgia.fbook.screen.approverequest.approvedetail

import com.framgia.fbook.data.model.User

/**
 * Created by Hyperion on 29/09/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
interface GetListUserListener {
  fun onGetListUserWaiting(listUser: List<User>?)

  fun onGetListUserReading(listUser: List<User>?)

  fun onGetListUserReturning(listUser: List<User>?)

  fun onGetListUserReturned(listUser: List<User>?)
}
