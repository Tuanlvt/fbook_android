package com.framgia.fbook.screen.userinbookdetail.screen.UserReadingWaitingReturn

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Owner
import com.framgia.fbook.data.model.User
import com.framgia.fbook.databinding.FragmentUserReadingWaitingReturnBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.userinbookdetail.UserInBookDetailActivity
import javax.inject.Inject

/**
 * UserReadingWaitingReturn Screen.
 */
class UserReadingWaitingReturnFragment : BaseFragment(), UserReadingWaitingReturnContract.ViewModel {

  companion object {

    private val USERS_EXTRA: String = "USERS_EXTRA"
    private val OWNERS_EXTRA: String = "OWNERS_EXTRA"

    fun newInstance(users: List<User>?, owners: List<Owner>?): UserReadingWaitingReturnFragment {
      val fragment = UserReadingWaitingReturnFragment()
      val bundle = Bundle()
      bundle.putParcelableArrayList(USERS_EXTRA, users as ArrayList<User>)
      bundle.putParcelableArrayList(OWNERS_EXTRA, owners as ArrayList<Owner>)
      fragment.arguments = bundle
      return fragment
    }
  }

  @Inject
  internal lateinit var mPresenter: UserReadingWaitingReturnContract.Presenter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerUserReadingWaitingReturnComponent.builder()
        .userInBookDetailComponent(
            (activity as UserInBookDetailActivity).getUserInBookDetailComponent())
        .userReadingWaitingReturnModule(UserReadingWaitingReturnModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentUserReadingWaitingReturnBinding>(inflater,
        R.layout.fragment_user_reading_waiting_return, container,
        false)
    binding.viewModel = this
    initData()
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

  private fun initData() {
    val users: List<User> = arguments.getParcelableArrayList(USERS_EXTRA)
    val owners: List<Owner> = arguments.getParcelableArrayList(OWNERS_EXTRA)
    
    //TODO edit later
    updateAdapter(users)
  }

  private fun updateAdapter(users: List<User>?) {
    //TODO edit later
  }
}
