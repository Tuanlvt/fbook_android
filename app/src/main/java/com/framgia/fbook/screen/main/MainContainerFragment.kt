package com.framgia.fbook.screen.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.mainpage.MainPageFragment
import com.framgia.fbook.screen.menuprofile.MenuProfileFragment
import com.framgia.fbook.screen.mybook.MyBookFragment
import com.framgia.fbook.screen.notification.NotificationContainerFragment
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.NavigateAnim
import com.framgia.fbook.utils.navigator.Navigator

/**
 * Created by levutantuan on 8/30/17.
 */
class MainContainerFragment : BaseFragment() {

  private val mNavigator: Navigator by lazy { Navigator(this) }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val view = inflater?.inflate(R.layout.fragment_main_container, container, false)
    val tab = arguments.getInt(EXTRA_TAB_FOOTER)
    val containerId = R.id.layout_content_main
    when (tab) {
      Constant.Tab.TAB_HOME -> mNavigator.goNextChildFragment(containerId,
          MainPageFragment.newInstance(), true, NavigateAnim.NONE, MainPageFragment.TAG)
      Constant.Tab.TAB_MY_BOOK -> mNavigator.goNextChildFragment(containerId,
          MyBookFragment.newInstance(), true, NavigateAnim.NONE, MyBookFragment.TAG)
      Constant.Tab.TAB_NOTIFICATION -> mNavigator.goNextChildFragment(containerId,
          NotificationContainerFragment.newInstance(), true, NavigateAnim.NONE, MyBookFragment.TAG)
      Constant.Tab.TAB_ACCOUNT -> mNavigator.goNextChildFragment(containerId,
          MenuProfileFragment.newInstance(), false, NavigateAnim.NONE,
          MenuProfileFragment.TAG)
    }
    return view
  }

  fun onBackPressed(): Boolean = mNavigator.goBackChildFragment()

  companion object {

    private val EXTRA_TAB_FOOTER = "EXTRA_TAB_FOOTER"

    fun newInstance(tab: Int): MainContainerFragment {
      val fragment = MainContainerFragment()
      val bundle = Bundle()
      bundle.putInt(EXTRA_TAB_FOOTER, tab)
      fragment.arguments = bundle
      return fragment
    }
  }

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (!isAdded) {
      return
    }
    val fragment: Fragment = childFragmentManager.findFragmentById(
        R.id.layout_content_main) ?: return
    fragment.userVisibleHint = isVisibleToUser
  }
}
