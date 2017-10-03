package com.framgia.fbook.screen.approverequest.approvedetail.waitandreadrequest

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.databinding.FragmentWaitandreadrequestBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.approverequest.approvedetail.ApproveDetailActivity
import com.framgia.fbook.screen.approverequest.approvedetail.GetListUserListener
import com.framgia.fbook.screen.approverequest.approvedetail.adapterlistrequest.ListRequestAdapter
import javax.inject.Inject

/**
 * WaitAndReadRequest Screen.
 */
class WaitAndReadRequestFragment : BaseFragment(), WaitAndReadRequestContract.ViewModel, GetListUserListener.GetListUserWaitAndRead {

  @Inject
  internal lateinit var mPresenter: WaitAndReadRequestContract.Presenter
  @Inject
  internal lateinit var mListRequestAdapter: ListRequestAdapter

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

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is ApproveDetailActivity) {
      context.setGetListUserWaitAndRead(this)
    }
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onGetListUserWaiting(listUser: List<User>?) {
    mListRequestAdapter.updateData(listUser)
  }

  override fun onGetListUserReading(listUser: List<User>?) {
    listUser?.let {
      mListRequestAdapter.setIsApproved(!listUser.isEmpty())
      mListRequestAdapter.updateData(listUser)
    }
  }

  companion object {

    private val TAG = WaitAndReadRequestFragment::class.java.simpleName

    fun newInstance(): WaitAndReadRequestFragment {
      return WaitAndReadRequestFragment()
    }
  }
}
