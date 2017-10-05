package com.framgia.fbook.screen.notification

import android.databinding.BaseObservable
import com.framgia.fbook.data.model.ItemNotification
import com.framgia.fbook.screen.onItemRecyclerViewClickListener

/**
 * Created by Hyperion on 05/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ItemNotificationViewModel(val itemNotification: ItemNotification,
    private val mOnItemRecyclerViewClickListener: onItemRecyclerViewClickListener) : BaseObservable() {

  companion object {
    private val BLANK = " "
    //TODO Edit later
    private val WAITING = "waiting"
    private val REVIEW = "review"
    private val CANCEL = "cancel"
  }

  fun onItemClicked() {
    mOnItemRecyclerViewClickListener.onItemClickListener(itemNotification)
  }

  fun getName(): String {
    return itemNotification.user?.name + BLANK + "[" + itemNotification.user?.email + "]"
  }
}
