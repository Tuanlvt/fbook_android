package com.framgia.fbook.screen.otheruser

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.framgia.fbook.R
import com.framgia.fbook.screen.otheruser.bookInUser.BookInUserFragment
import com.framgia.fbook.utils.Constant

/**
 * Created by levutantuan on 9/27/17.
 */
class OtherUserAdapter(private val mContext: Context,
    fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager) {

  companion object {
    val TOTAL_TAB: Int = 6
  }

  override fun getItem(position: Int): Fragment {
    return when (position) {
      Constant.TabOtherInUser.TAB_READING_BOOK -> BookInUserFragment.newInstance(
          Constant.RequestCodeBookInUser.TAB_READING_BOOK)

      Constant.TabOtherInUser.TAB_WAITING_BOOK -> BookInUserFragment.newInstance(
          Constant.RequestCodeBookInUser.TAB_WAITING_BOOK)
      Constant.TabOtherInUser.TAB_READ_BOOK -> BookInUserFragment.newInstance(
          Constant.RequestCodeBookInUser.TAB_READING_BOOK)
      Constant.TabOtherInUser.TAB_SHARING_BOOK -> BookInUserFragment.newInstance(
          Constant.RequestCodeBookInUser.TAB_SHARING_BOOK)
      Constant.TabOtherInUser.TAB_SUGGESTED_BOOK -> BookInUserFragment.newInstance(
          Constant.RequestCodeBookInUser.TAB_DONE_BOOK)
      Constant.TabOtherInUser.TAB_REVIEW_BOOK -> BookInUserFragment.newInstance(
          Constant.RequestCodeBookInUser.TAB_REVIEWED_BOOK)
      else -> {
        Fragment()
      }
    }
  }

  override fun getPageTitle(position: Int): CharSequence {
    when (position) {
      Constant.TabOtherInUser.TAB_READING_BOOK -> return mContext.getString(R.string.reading_books)
      Constant.TabOtherInUser.TAB_WAITING_BOOK -> return mContext.getString(R.string.waiting_books)
      Constant.TabOtherInUser.TAB_READ_BOOK -> return mContext.getString(R.string.read_book)
      Constant.TabOtherInUser.TAB_SHARING_BOOK -> return mContext.getString(R.string.sharing_books)
      Constant.TabOtherInUser.TAB_SUGGESTED_BOOK -> return mContext.getString(
          R.string.suggested_books)
      Constant.TabOtherInUser.TAB_REVIEW_BOOK -> return mContext.getString(R.string.reviewed_books)
    }
    return Constant.EXTRA_EMTY
  }

  override fun getCount(): Int = OtherUserAdapter.TOTAL_TAB
}
