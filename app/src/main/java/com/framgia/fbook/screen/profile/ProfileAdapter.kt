package com.framgia.fbook.screen.profile

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.framgia.fbook.R

/**
 * Created by levutantuan on 9/21/17.
 */
class ProfileAdapter(private val mContext: Context,
    fragment: FragmentManager) : FragmentPagerAdapter(fragment) {
  private val mFragments: MutableList<Fragment> = ArrayList<Fragment>()

  companion object {
    val TOTAL_TAB: Int = 2
  }

  init {
    for (i in 0..TOTAL_TAB - 1) {
      mFragments.add(Fragment())
    }
  }

  override fun getItem(position: Int): Fragment {
    val fragment = ProfileContainerFragment.newInstance(position)
    mFragments[position] = fragment
    return fragment
  }

  override fun getPageTitle(position: Int): CharSequence {
    when (position) {
      ProfileContainerFragment.PROFILE -> return mContext.getString(R.string.personal_infor)
      ProfileContainerFragment.CATEGORY_FAVORITE -> return mContext.getString(
          R.string.category_favorite)
    }
    return ""
  }

  override fun getCount(): Int = TOTAL_TAB

  fun getFragment(position: Int?): Fragment? {
    try {
      return position?.let { mFragments[it] }
    } catch (e: IndexOutOfBoundsException) {
      return null
    }
  }
}
