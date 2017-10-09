package com.framgia.fbook.screen.categoryfavorite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.FragmentCategoryFavoriteBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.addCategoryFavorite.AddCategoryFavoriteActivity
import com.framgia.fbook.screen.profile.GetUserListener
import com.framgia.fbook.screen.profile.ProfileActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import javax.inject.Inject

/**
 * CategoryFavorite Screen.
 */
class CategoryFavoriteFragment : BaseFragment(), CategoryFavoriteContract.ViewModel, GetUserListener.onGetUserCategory {

  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mPresenter: CategoryFavoriteContract.Presenter
  @Inject
  internal lateinit var mAdapter: CategoryAdapter
  @Inject
  internal lateinit var mUserRepository: UserRepository
  val mUser: ObservableField<User> = ObservableField()
  val mIsViSiBleButtonUpdateCategoryFavorite: ObservableField<Boolean> = ObservableField(false)

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

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is ProfileActivity) {
      context.setGetUserListenerCategory(this)
    }
  }

  override fun onGetUser(user: User?) {
    mUser.set(user)
    if (mUserRepository.getUserLocal()?.id != mUser.get().id) {
      mIsViSiBleButtonUpdateCategoryFavorite.set(false)
    } else {
      mIsViSiBleButtonUpdateCategoryFavorite.set(true)
    }
    mAdapter.updateData(mUser.get().categories)
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK && requestCode == Constant.RequestCode.TAB_CATEGORY_FAVORITE_USER) {
      mPresenter.getUser()
    }
  }

  override fun onGetUserSuccess(user: User?) {
    user.let { mUser.set(user) }
    mUserRepository.saveUser(user)
    mAdapter.updateData(mUser.get().categories)
  }

  override fun onError(exception: BaseException) {
    Log.d(TAG, exception.getMessageError())
  }

  private fun fillData() {
    mUser.let {
      it.set(mUserRepository.getUserLocal())
      mAdapter.updateData(it.get()?.categories)
    }
    mIsViSiBleButtonUpdateCategoryFavorite.set(true)
  }

  fun onClickEditCategoryFavorite() {
    mNavigator.startActivityForResultFromFragment(AddCategoryFavoriteActivity::class.java,
        Constant.RequestCode.TAB_CATEGORY_FAVORITE_USER)
  }

  companion object {
    val TAG: String = CategoryFavoriteFragment::class.java.name

    fun newInstance(): CategoryFavoriteFragment {
      return CategoryFavoriteFragment()
    }
  }
}
