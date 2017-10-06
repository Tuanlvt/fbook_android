package com.framgia.fbook.screen.notification

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.framgia.fbook.R
import com.framgia.fbook.screen.notification.notificationUser.NotificationFragment
import com.framgia.fbook.utils.Constant

/**
 * Created by Hyperion on 06/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class NotificationContainerAdapter(private val context: Context,
    fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager) {

  companion object {
    val TOTAL_TAB: Int = 2
  }

  override fun getItem(position: Int): Fragment {
    return when (position) {
      Constant.TabNotification.TAB_USER -> NotificationFragment.newInstance()
    //TODO edit later
    //Constant.TabNotification.TAB_FOLLOW ->
      else -> {
        Fragment()
      }
    }
  }

  override fun getPageTitle(position: Int): CharSequence {
    when (position) {
      Constant.TabNotification.TAB_USER -> return context.getString(
          R.string.notification)
      Constant.TabNotification.TAB_FOLLOW -> return context.getString(
          R.string.newsfeed)
    }
    return ""
  }

  override fun getCount(): Int = TOTAL_TAB
}
