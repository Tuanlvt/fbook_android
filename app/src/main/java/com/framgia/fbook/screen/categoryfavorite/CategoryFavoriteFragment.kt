package com.framgia.fbook.screen.categoryfavorite

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.databinding.FragmentCategoryFavoriteBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.addCategoryFavorite.AddCategoryFavoriteActivity
import com.framgia.fbook.screen.profile.ProfileActivity
import com.framgia.fbook.utils.navigator.Navigator
import javax.inject.Inject

/**
 * CategoryFavorite Screen.
 */
class CategoryFavoriteFragment : BaseFragment(), CategoryFavoriteContract.ViewModel {

  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mPresenter: CategoryFavoriteContract.Presenter
  @Inject
  internal lateinit var mAdapter: CategoryAdapter
  @Inject
  internal lateinit var mUserRepository: UserRepository
  val mUser: ObservableField<User>? = ObservableField()

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerCategoryFavoriteComponent.builder()
        .profileComponent((activity as ProfileActivity).getProfileComponent())
        .categoryFavoriteModule(CategoryFavoriteModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentCategoryFavoriteBinding>(inflater,
        R.layout.fragment_category_favorite, container,
        false)
    binding.viewModel = this
    fillData()
    return binding.root
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  private fun fillData() {
    mUser.let {
      it?.set(mUserRepository.getUserLocal())
      mAdapter.updateData(it?.get()?.categories)
    }
  }

  fun onClickEditCategoryFavorite() {
    mNavigator.startActivity(AddCategoryFavoriteActivity::class.java)
  }

  companion object {
    val TAG: String = CategoryFavoriteFragment::class.java.name

    fun newInstance(): CategoryFavoriteFragment {
      return CategoryFavoriteFragment()
    }
  }
}
