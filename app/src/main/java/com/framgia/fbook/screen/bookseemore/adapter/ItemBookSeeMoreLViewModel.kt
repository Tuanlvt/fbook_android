package com.framgia.fbook.screen.bookseemore.adapter

import android.databinding.BaseObservable
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.screen.onItemRecyclerViewClickListener

/**
 * Created by levutantuan on 10/20/17.
 */
class ItemBookSeeMoreLViewModel(val book: Book,
    private val mItemClickListener: onItemRecyclerViewClickListener?) : BaseObservable() {

  fun onItemClicked() {
    mItemClickListener?.onItemClickListener(book)
  }
}
