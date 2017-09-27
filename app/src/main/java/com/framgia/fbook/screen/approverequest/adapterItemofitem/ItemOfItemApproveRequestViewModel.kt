package com.framgia.fbook.screen.approverequest.adapterItemofitem

import android.databinding.BaseObservable
import com.framgia.fbook.data.model.User

/**
 * Created by Hyperion on 26/09/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ItemOfItemApproveRequestViewModel(private val mBookId: Int?, val user: User,
    private val mItemOfItemApproveClickListener: ItemOfItemApproveClickListener?) : BaseObservable() {

  fun onClickApprove() {
    mItemOfItemApproveClickListener?.onClickApprove(mBookId, user.id)
  }
}
