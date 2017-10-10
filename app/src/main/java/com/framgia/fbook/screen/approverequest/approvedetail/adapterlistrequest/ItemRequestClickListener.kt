package com.framgia.fbook.screen.approverequest.approvedetail.adapterlistrequest

import com.framgia.fbook.data.model.User

/**
 * Created by Hyperion on 04/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
interface ItemRequestClickListener {
  fun onItemRequestClick(pivot: User.Pivot?, isApprove: Boolean)
}
