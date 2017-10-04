package com.framgia.fbook.screen.approverequest.approvedetail.returningandreturnedRequest

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.databinding.FragmentReturningandreturnedfragmentBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.approverequest.approvedetail.ApproveDetailActivity
import javax.inject.Inject

/**
 * ReturningAndReturnedFragment Screen.
 */
class ReturningAndReturnedFragmentFragment : BaseFragment(), ReturningAndReturnedFragmentContract.ViewModel {

  @Inject
  internal lateinit var mPresenter: ReturningAndReturnedFragmentContract.Presenter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerReturningAndReturnedFragmentComponent.builder()
        .approveDetailComponent((activity as ApproveDetailActivity).getApproveRequestComponent())
        .returningAndReturnedFragmentModule(ReturningAndReturnedFragmentModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentReturningandreturnedfragmentBinding>(inflater,
        R.layout.fragment_returningandreturnedfragment,
        container, false)
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
    private val TAG = ReturningAndReturnedFragmentFragment::class.java.simpleName

    fun newInstance(): ReturningAndReturnedFragmentFragment {
      return ReturningAndReturnedFragmentFragment()
    }
  }
}
