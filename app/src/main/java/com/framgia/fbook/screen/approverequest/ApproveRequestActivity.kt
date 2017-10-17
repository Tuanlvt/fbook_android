package com.framgia.fbook.screen.approverequest

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.util.Log
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.ActivityApproveRequestBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.screen.approverequest.approvedetail.ApproveDetailActivity
import com.framgia.fbook.screen.bookdetail.BookDetailActivity
import com.framgia.fbook.screen.mybook.MyBookFragment
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * ApproveRequest Screen.
 */
class ApproveRequestActivity : BaseActivity(), ApproveRequestContract.ViewModel, ItemApproveRequestClickListener {
  @Inject
  internal lateinit var mPresenter: ApproveRequestContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mApproveRequestAdapter: ApproveRequestAdapter
  @Inject
  internal lateinit var mNavigator: Navigator
  val mIsVisibleLayoutNoData: ObservableField<Boolean> = ObservableField()
  private var mPosition: Int = 0

  companion object {
    private val TAG = ApproveRequestActivity::class.java.simpleName
    private val APPROVE = "approve"
    private val UNAPPROVE = "unapprove"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerApproveRequestComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .approveRequestModule(ApproveRequestModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.setContentView<ActivityApproveRequestBinding>(this,
        R.layout.activity_approve_request)

    mApproveRequestAdapter.setItemApproveRequestListener(this)
    mPresenter.getApproveRequest()
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

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Constant.ResultCode.UNAPPROVE
        && requestCode == Constant.RequestCode.APRROVE_BOOK_REQUEST) {
      mApproveRequestAdapter.updateNumberOfWaitingIncrease(mPosition)
    }
    if (resultCode == Constant.ResultCode.APRROVE
        && requestCode == Constant.RequestCode.APRROVE_BOOK_REQUEST) {
      mApproveRequestAdapter.updateNumberOfWaitingDecrease(mPosition)
    }
  }

  override fun onGetApproveRequestSuccess(listBook: List<Book>?) {
    listBook?.let {
      listBook.forEach { it.numberOfWaiting = it.usersWaitings?.size }
      mApproveRequestAdapter.updateData(it)
    }
    setVisibleLayoutNoData(listBook?.size)
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onApproveBookSuccess() {
  }

  override fun onError(e: BaseException) {
    Log.e(MyBookFragment.TAG, e.getMessageError())
  }

  override fun onCLickViewRequest(bookId: Int?, position: Int) {
    mPosition = position
    bookId?.let {
      val bundle = Bundle()
      bundle.putInt(Constant.BOOK_DETAIL_EXTRA, bookId)
      mNavigator.startActivityForResult(ApproveDetailActivity::class.java, bundle,
          Constant.RequestCode.APRROVE_BOOK_REQUEST)
    }
  }

  override fun onItemClick(book: Book) {
    val bundle = Bundle()
    book.id?.let {
      bundle.putInt(Constant.BOOK_DETAIL_EXTRA, it)
    }
    mNavigator.startActivity(BookDetailActivity::class.java, bundle)
  }

  private fun setVisibleLayoutNoData(size: Int?) {
    if (size == 0) {
      mIsVisibleLayoutNoData.set(true)
      return
    }
    mIsVisibleLayoutNoData.set(false)
  }

  fun onClickReloadData() {
    mPresenter.getApproveRequest()
  }

  fun onClickArrowBack() {
    mNavigator.finishActivity()
  }
}
