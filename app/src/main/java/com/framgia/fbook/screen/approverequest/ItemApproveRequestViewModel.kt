package com.framgia.fbook.screen.approverequest

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.screen.approverequest.adapterItemofitem.ItemApproveRequestAdapter
import com.framgia.fbook.screen.approverequest.adapterItemofitem.ItemOfItemApproveClickListener

/**
 * Created by framgia on 22/09/2017.
 */
class ItemApproveRequestViewModel(val context: Context, val mBook: Book,
    private val mItemClickListener: ItemApproveRequestClickListener?) : BaseObservable(), ItemOfItemApproveClickListener {
  private var mIsVisibleListAllRequest = false

  fun onItemBookClicked() {
    mItemClickListener?.onItemClick(mBook)
  }

  fun onClickViewRequest() {
    setIsVisibleListAllRequest(!mIsVisibleListAllRequest)
  }

  override fun onClickApprove(bookId: Int?, userId: Int?) {
    mItemClickListener?.onClickApprove(bookId, userId)
  }

  fun getNumberOfWaiting(): String {
    return String.format(context.getString(R.string.number_requests),
        mBook.usersWaitings?.size.toString())
  }

  fun getAdapter(): ItemApproveRequestAdapter {
    val itemApproveRequestAdapter = ItemApproveRequestAdapter(context, mBook.id,
        mBook.usersWaitings)
    itemApproveRequestAdapter.setItemOfItemApproveClickListener(this)
    return itemApproveRequestAdapter
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
