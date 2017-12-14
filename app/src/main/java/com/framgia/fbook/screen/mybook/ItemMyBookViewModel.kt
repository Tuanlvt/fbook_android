package com.framgia.fbook.screen.mybook

import android.databinding.BaseObservable
import android.view.View
import com.framgia.fbook.data.model.Book

/**
 * Created by framgia on 05/09/2017.
 */
class ItemMyBookViewModel(val book: Book,
    private val mItemClickListener: ItemMyBookClickListener?) : BaseObservable() {
  fun onItemClicked(view: View) {
    mItemClickListener?.onItemMyBookClick(book)
  }

  fun onClickViewAllRequest(view: View) {
    mItemClickListener?.onClickViewAllRequest()
  }

  fun onClickEditBook(view: View) {
    mItemClickListener?.editClickListener(book)
  }
}
