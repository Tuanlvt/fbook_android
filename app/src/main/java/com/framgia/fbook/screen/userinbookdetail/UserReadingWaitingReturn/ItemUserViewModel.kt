package com.framgia.fbook.screen.userinbookdetail.UserReadingWaitingReturn

import android.databinding.BaseObservable
import android.view.View
import com.framgia.fbook.data.model.User

/**
 * Created by framgia on 27/09/2017.
 */
class ItemUserViewModel(val user: User?,
    private val mItemUserClickListener: ItemUserClickListener?) : BaseObservable() {
  fun onItemUserClick(view: View) {
    mItemUserClickListener?.onItemUserClick(user)
  }
}
