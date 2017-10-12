package com.framgia.fbook.screen.notification

import android.content.Context
import android.databinding.BaseObservable
import com.framgia.fbook.R
import com.framgia.fbook.data.model.ItemNotification
import com.framgia.fbook.screen.onItemRecyclerViewClickListener

/**
 * Created by Hyperion on 05/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ItemNotificationViewModel(private val mContext: Context,
    val itemNotification: ItemNotification,
    private val mOnItemRecyclerViewClickListener: onItemRecyclerViewClickListener,
    val type: Int) : BaseObservable() {

  companion object {
    private val BLANK = " "
    //TODO Edit later
     val WAITING = "waiting"
     val REVIEW = "review"
     val CANCEL = "cancel"
     val APPROVE_WAITING = "approve_waiting"
     val UNAPPROVE_WAITING = "unapprove_waiting"
     val APPROVE_RETURNING = "approve_returning"
     val ADD_OWNER = "add_owner"
     val REMOVE_OWNER = "remove_owner"
     val RETURNING = "returning"
     val TWO_DOT = ":"
  }

  fun onItemClicked() {
    mOnItemRecyclerViewClickListener.onItemClickListener(itemNotification)
  }

  fun getName(): String {
    return itemNotification.user?.name + BLANK + "[" + itemNotification.user?.email + "]"
  }

  fun getType(): String {
    return when (itemNotification.type) {
      WAITING -> mContext.getString(R.string.waiting_book) + BLANK + itemNotification.book?.title
      REVIEW -> mContext.getString(R.string.review_book) + BLANK + itemNotification.book?.title
      CANCEL -> mContext.getString(R.string.cancel_book) + BLANK + itemNotification.book?.title
      APPROVE_WAITING -> mContext.getString(
          R.string.accept_your_request_waiting_book) + BLANK + itemNotification.book?.title
      UNAPPROVE_WAITING -> mContext.getString(
          R.string.accept_your_request_waiting_book) + BLANK + itemNotification.book?.title
      APPROVE_RETURNING -> mContext.getString(
          R.string.return_books_successfully) + BLANK + itemNotification.book?.title
      ADD_OWNER -> mContext.getString(R.string.add_book) + BLANK + itemNotification.book?.title
      REMOVE_OWNER -> mContext.getString(
          R.string.remove_book) + BLANK + itemNotification.book?.title
      RETURNING -> mContext.getString(
          R.string.return_book) + TWO_DOT + BLANK + itemNotification.book?.title
      else -> ""
    }
  }

  fun getVisibleType(): Boolean {
    return type == 0
  }
}
