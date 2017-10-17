package com.framgia.fbook.screen.approverequest

import android.content.Context
import android.databinding.BaseObservable
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book

/**
 * Created by framgia on 22/09/2017.
 */
class ItemApproveRequestViewModel(val context: Context, val mBook: Book,
    private val mItemClickListener: ItemApproveRequestClickListener?,
    private val mPosition: Int) : BaseObservable() {

  fun onItemBookClicked() {
    mItemClickListener?.onItemClick(mBook)
  }

  fun onClickViewRequest() {
    mItemClickListener?.onCLickViewRequest(mBook.id, mPosition)
  }

  fun getNumberOfWaiting(): String {
    return String.format(context.getString(R.string.number_requests),
        mBook.numberOfWaiting.toString())
  }

}
