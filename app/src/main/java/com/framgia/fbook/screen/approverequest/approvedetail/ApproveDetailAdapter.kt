package com.framgia.fbook.screen.approverequest.approvedetail

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.framgia.fbook.R
import com.framgia.fbook.screen.approverequest.approvedetail.returningandreturnedRequest.ReturningAndReturnedFragmentFragment
import com.framgia.fbook.screen.approverequest.approvedetail.waitandreadrequest.WaitAndReadRequestFragment
import com.framgia.fbook.utils.Constant

/**
 * Created by Hyperion on 28/09/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ApproveDetailAdapter(private val context: Context,
    fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager) {

  companion object {
    val TOTAL_TAB: Int = 2
  }

  override fun getItem(position: Int): Fragment {
    return when (position) {
      Constant.TabApproveDetail.TAB_WAIT_AND_READ -> WaitAndReadRequestFragment.newInstance()
      Constant.TabApproveDetail.TAB_RETURNING_AND_RETURNED -> ReturningAndReturnedFragmentFragment.newInstance()
      else -> {
        Fragment()
      }
    }
  }

  override fun getPageTitle(position: Int): CharSequence {
    when (position) {
      Constant.TabApproveDetail.TAB_WAIT_AND_READ -> return context.getString(
          R.string.approve_waiting_reading_requests)
      Constant.TabApproveDetail.TAB_RETURNING_AND_RETURNED -> return context.getString(
          R.string.approve_returning_returned_requests)
    }
    return ""
  }

  override fun getCount(): Int = TOTAL_TAB
}
