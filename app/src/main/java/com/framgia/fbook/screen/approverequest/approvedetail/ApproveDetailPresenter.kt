package com.framgia.fbook.screen.approverequest.approvedetail;

/**
 * Listens to user actions from the UI ({@link ApproveDetailActivity}), retrieves the data and updates
 * the UI as required.
 */
class ApproveDetailPresenter : ApproveDetailContract.Presenter {

  private var mViewModel: ApproveDetailContract.ViewModel? = null

  override fun onStart() {}

  override fun onStop() {}

  override fun setViewModel(viewModel: ApproveDetailContract.ViewModel) {
    mViewModel = viewModel
  }
}
