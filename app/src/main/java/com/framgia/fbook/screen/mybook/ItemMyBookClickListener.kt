package com.framgia.fbook.screen.mybook

import com.framgia.fbook.data.model.Book

/**
 * Created by framgia on 05/09/2017.
 */
interface ItemMyBookClickListener {
  fun onItemMyBookClick(book: Book)

  fun onClickViewAllRequest()
}
