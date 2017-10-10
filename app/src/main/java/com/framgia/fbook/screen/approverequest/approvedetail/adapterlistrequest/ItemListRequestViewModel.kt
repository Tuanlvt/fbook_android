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
    private val mItemRequestClickListener: ItemRequestClickListener?) : BaseObservable() {
  private var mVisibleApprove = false

  fun onItemClicked() {
    mItemRequestClickListener?.onItemRequestClick(user.pivot, mIsApproved)
  }

  fun getTextApprove(): String {
    if (!mIsWaiting) {
      setVisibleApprove(true)
      return mContext.getString(R.string.approve)
    } else {
      if (mIsApproved) {
        setVisibleApprove(true)
        return mContext.getString(R.string.unapprove)
      }
      if (!getVisibleApprove()) {
        return mContext.getString(R.string.approve)
      }
    }
    return ""
  }

  @Bindable
  fun getVisibleApprove(): Boolean {
    return mVisibleApprove
  }

  fun setVisibleApprove(approve: Boolean) {
    mVisibleApprove = approve
    notifyPropertyChanged(BR.visibleApprove)
  }
}
