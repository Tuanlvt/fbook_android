package com.framgia.fbook.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.categoryfavorite.CategoryFavoriteFragment
import com.framgia.fbook.screen.personalinfor.PersonalInforFragment
import com.framgia.fbook.utils.navigator.NavigateAnim
import com.framgia.fbook.utils.navigator.Navigator

/**
 * Created by levutantuan on 9/21/17.
 */
class ProfileContainerFragment : BaseFragment() {

  private val mNavigator: Navigator by lazy { Navigator(this) }

  companion object {

    val EXTRA_TAB: String = "EXTRA_TAB"
    val PROFILE: Int = 0
    val CATEGORY_FAVORITE: Int = 1

    fun newInstance(position: Int): ProfileContainerFragment {
      var profileContainerFragment: ProfileContainerFragment = ProfileContainerFragment()
      var bundle: Bundle = Bundle()
      bundle.putInt(EXTRA_TAB, position)
      profileContainerFragment.arguments = bundle
      return profileContainerFragment
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val view: View? = inflater?.inflate(R.layout.fragment_profile_container, container, false)
    var containerId = R.id.layout_profile_container
    var page: Int = arguments.getInt(EXTRA_TAB)
    when (page) {
      PROFILE -> mNavigator.goNextChildFragment(containerId, PersonalInforFragment.newInstance(),
          false, NavigateAnim.NONE, PersonalInforFragment.TAG)
      CATEGORY_FAVORITE ->
        mNavigator.goNextChildFragment(containerId,
            CategoryFavoriteFragment.newInstance(), false, NavigateAnim.NONE,
            CategoryFavoriteFragment.TAG)
    }
    return view
  }
}
