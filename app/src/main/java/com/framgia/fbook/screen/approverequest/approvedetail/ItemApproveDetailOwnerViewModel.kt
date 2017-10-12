package com.framgia.fbook.screen.approverequest.approvedetail

import android.databinding.BaseObservable
import android.view.View
import com.framgia.fbook.data.model.Owner
import com.framgia.fbook.screen.bookdetail.ItemOwnerClickListener

/**
 * Created by Hyperion on 29/09/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ItemApproveDetailOwnerViewModel(val mOwner: Owner,
    private val mOnItemOwnerClickListener: ItemOwnerClickListener?) : BaseObservable() {

  fun onItemOwnerClick(view: View) {
    mOnItemOwnerClickListener?.onItemOwnerClick(mOwner)
  }
}
