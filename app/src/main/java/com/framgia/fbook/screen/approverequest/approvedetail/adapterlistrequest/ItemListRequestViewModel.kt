package com.framgia.fbook.screen.approverequest.approvedetail.adapterlistrequest

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User

/**
 * Created by Hyperion on 29/09/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ItemListRequestViewModel(private val mContext: Context, val user: User,
    private val mIsApproved: Boolean, private val mIsWaiting: Boolean,
    private val mIsReturned: Boolean,
    private val mItemRequestClickListener: ItemRequestClickListener?) : BaseObservable() {
  private var mVisibleApprove = false

  fun onItemClicked() {
    mItemRequestClickListener?.onItemRequestClick(user.pivot, mIsApproved)
  }

  fun getTextApprove(): String {
    if (!mIsWaiting) {
      setVisibleApproveOrReturning(true)
      return mContext.getString(R.string.approve)
    } else {
      if (mIsApproved) {
        setVisibleApproveOrReturning(true)
        return mContext.getString(R.string.unapprove)
      } else if (!getVisibleButtonApproveOrUnApprove()) {
        return mContext.getString(R.string.approve)
      }
    }
    return ""
  }

  fun getStatusUser(): Boolean {
    return mIsApproved
  }

  fun getIsVisibleLayoutWait(): Boolean {
    return !mIsReturned
  }

  @Bindable
  fun getVisibleButtonApproveOrUnApprove(): Boolean {
    return mVisibleApprove
  }

  fun setVisibleApproveOrReturning(approve: Boolean) {
    mVisibleApprove = approve
    notifyPropertyChanged(BR.visibleButtonApproveOrUnApprove)
  }
}
