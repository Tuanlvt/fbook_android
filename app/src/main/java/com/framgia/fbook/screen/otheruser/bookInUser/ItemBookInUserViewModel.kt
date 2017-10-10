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
    val positionTab: Int?) : BaseObservable() {
  val name: String = book?.author.toString()
  private lateinit var mReturnBookClick: OnReturnBookListener

  fun getVisibleButtonReturnBook(): Boolean {
    return positionTab == 0
  }

  fun onItemBookInUserClick(view: View) {
    mItemBookInUserClickListener?.onItemBookInUserClick(book)
  }

  fun onReturnBookClick(view: View) {
    mReturnBookClick.onReturnBookClick()
  }
}
