package com.framgia.fbook.screen.approverequest.approvedetail.waitandreadrequest

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.databinding.FragmentWaitandreadrequestBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.approverequest.approvedetail.ApproveDetailActivity
import javax.inject.Inject

/**
 * WaitAndReadRequest Screen.
 */
class WaitAndReadRequestFragment : BaseFragment(), WaitAndReadRequestContract.ViewModel {

  @Inject
  internal lateinit var mPresenter: WaitAndReadRequestContract.Presenter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerWaitAndReadRequestComponent.builder()
        .approveDetailComponent((activity as ApproveDetailActivity).getApproveRequestComponent())
        .waitAndReadRequestModule(WaitAndReadRequestModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentWaitandreadrequestBinding>(inflater,
        R.layout.fragment_waitandreadrequest, container,
        false)
    binding.viewModel = this
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

  companion object {

    fun newInstance(): WaitAndReadRequestFragment {
      return WaitAndReadRequestFragment()
    }
  }
}
