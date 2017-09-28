package com.framgia.fbook.screen.approverequest.approvedetail;

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.databinding.ActivityApprovedetailBinding
import com.framgia.fbook.screen.BaseActivity
import javax.inject.Inject

/**
 * ApproveDetailActivity Screen.
 */
class ApproveDetailActivity : BaseActivity(), ApproveDetailContract.ViewModel {

  @Inject
  internal lateinit var mPresenter: ApproveDetailContract.Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerApproveDetailComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .approveDetailModule(ApproveDetailModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.setContentView<ActivityApprovedetailBinding>(this,
        R.layout.activity_approvedetail)
    binding.viewModel = this
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  fun onClickArrowBack() {
  }

  fun onClickSearch() {
  }
}
