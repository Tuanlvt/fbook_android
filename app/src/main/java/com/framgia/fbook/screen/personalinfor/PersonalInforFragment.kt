package com.framgia.fbook.screen.personalinfor

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.databinding.FragmentPersonalInforBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.profile.GetUserListener
import com.framgia.fbook.screen.profile.ProfileActivity
import javax.inject.Inject

/**
 * PersonalInfor Screen.
 */
class PersonalInforFragment : BaseFragment(), PersonalInforContract.ViewModel, GetUserListener.onGetUserPersonal {

  @Inject
  internal lateinit var mPresenter: PersonalInforContract.Presenter
  @Inject
  lateinit var mUserRepository: UserRepository
  val mUser: ObservableField<User> = ObservableField()

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerPersonalInforComponent.builder()
        .profileComponent((activity as ProfileActivity).getProfileComponent())
        .personalInforModule(PersonalInforModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentPersonalInforBinding>(inflater,
        R.layout.fragment_personal_infor, container, false)
    binding.viewModel = this
    mUser.set(mUserRepository.getUserLocal())
    return binding.root
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is ProfileActivity) {
      context.setGetUserListenerPersonal(this)
    }
  }

  override fun onGetUser(user: User?) {
    mUser.set(user)
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  fun setUser(user: User?) {
    mUser.set(user)
  }

  companion object {
    val TAG: String = PersonalInforFragment::class.java.name
    fun newInstance(): PersonalInforFragment {
      return PersonalInforFragment()
    }
  }
}
