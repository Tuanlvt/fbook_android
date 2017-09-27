package com.framgia.fbook.screen.approverequest

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.screen.approverequest.adapterItemofitem.ItemApproveRequestAdapter

/**
 * Created by framgia on 22/09/2017.
 */
class ItemApproveRequestViewModel(val context: Context, val mBook: Book,
    private val mItemClickListener: ItemApproveRequestClickListener?) : BaseObservable() {
  private var mIsVisibleListAllRequest = false

  fun onItemBookClicked() {
    mItemClickListener?.onItemClick(mBook)
  }

  fun onClickViewRequest() {
    setIsVisibleListAllRequest(!mIsVisibleListAllRequest)
  }

  fun getNumberOfWaiting(): String {
    return String.format(context.getString(R.string.number_requests),
        mBook.usersWaitings?.size.toString())
  }

  fun getAdapter(): ItemApproveRequestAdapter {
    return ItemApproveRequestAdapter(context, mBook.id, mBook.usersWaitings)
  }

  @Bindable
  fun getIsVisibleListAllRequest(): Boolean {
    return mIsVisibleListAllRequest
  }

  fun setIsVisibleListAllRequest(visibleListAllRequest: Boolean) {
    mIsVisibleListAllRequest = visibleListAllRequest
    notifyPropertyChanged(BR.isVisibleListAllRequest)
  }
}
