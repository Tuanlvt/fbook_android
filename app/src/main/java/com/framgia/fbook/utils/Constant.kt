package com.framgia.fbook.utils

/**
 * Created by le.quang.dao on 10/03/2017.
 */

object Constant {

  val END_POINT_URL = "http://api-book.framgia.vn"

  val BREAK_LINE = "\n"
  val LIST_BOOK_EXTRA = "LIST_BOOK_EXTRA"
  val LATE = "latest"
  val VIEW = "view"
  val RATING = "rating"
  val WAITING = "waiting"
  val READ = "read"
  val PAGE = 1
  val EXTRA_COMMA = ","
  val EXTRA_EMTY = ""
  val EXTRA_ZERO = 0

  val BOOK_DETAIL_EXTRA = "book_detail"
  val USER_BOOK_DETAIL_EXTRA = "user_book_detail"

  object Tab {
    val TAB_HOME: Int = 0
    val TAB_MY_BOOK: Int = 1
    val TAB_NOTIFICATION: Int = 2
    val TAB_ACCOUNT: Int = 3
  }

  object RequestCode {
    val TAB_MY_BOOK_REQUEST: Int = 101
    val TAB_PROFILE_REQUEST: Int = 102
    val BOOK_DETAIL_REQUEST: Int = 103
  }

  object TabUser {
    val TAB_USER_REVIEW = 0
    val TAB_USER_WAITING = 1
    val TAB_USER_READING = 2
    val TAB_USER_RETURNING = 3
    val TAB_USER_RETURNED = 4
  }

  object TabOtherInUser {
    val TAB_READING_BOOK = 0
    val TAB_WAITING_BOOK = 1
    val TAB_READ_BOOK = 2
    val TAB_SHARING_BOOK = 3
    val TAB_SUGGESTED_BOOK = 4
    val TAB_REVIEW_BOOK = 5
  }
}
