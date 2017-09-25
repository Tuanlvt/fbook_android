package com.framgia.fbook.screen.approverequest

import android.content.Context
import android.databinding.BaseObservable
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book

/**
 * Created by framgia on 22/09/2017.
 */
class ItemApproveRequestViewModel(val context: Context, val book: Book,
    private val mItemClickListener: ItemApproveRequestClickListener?) : BaseObservable() {
  fun onItemBookClicked() {
    mItemClickListener?.onItemClick(book)
  }

  fun onClickViewRequest() {
    mItemClickListener?.onClickViewRequest(book)
  }

  fun getNumberOfWaiting(): String {
    return String.format(context.getString(R.string.number_requests),
        book.usersWaitings?.size.toString())
  }


}
