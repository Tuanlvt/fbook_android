package com.framgia.fbook.screen.approverequest

import android.content.Context
import android.databinding.BaseObservable
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.utils.Constant

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
    var numberUserWaiting: Int = 0
    mBook.numberOfWaiting.let { numberUserWaiting = it as Int }
    if (numberUserWaiting >= Constant.MESSAGE_MAXIMUM) {
      return Constant.SHOW_MESSAGE_MAXIMUM
    }
    return numberUserWaiting.toString()
  }

  fun onGetVisibleBookMark(): Boolean {
    return mBook.usersWaitings?.size != null && mBook.usersWaitings?.size != 0
  }
}
