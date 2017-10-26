package com.framgia.fbook.screen.otheruser.bookInUser

import android.databinding.BaseObservable
import android.view.View
import com.framgia.fbook.data.model.Book

/**
 * Created by Hyperion on 10/6/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ItemBookInUserViewModel(val book: Book?,
    private val mItemBookInUserClickListener: ItemBookInUserClickListener?,
    private val mReturnBookListener: OnReturnBookListener,
    val positionTab: Int?, val checkCurrentUser: Boolean) : BaseObservable() {
  val name: String = book?.author.toString()

  fun getVisibleButtonReturnBook(): Boolean {
    return positionTab == 1 && checkCurrentUser
  }

  fun onItemBookInUserClick(view: View) {
    mItemBookInUserClickListener?.onItemBookInUserClick(book)
  }

  fun onReturnBookClick(view: View) {
    mReturnBookListener.onReturnBookClick(book)
  }
}
