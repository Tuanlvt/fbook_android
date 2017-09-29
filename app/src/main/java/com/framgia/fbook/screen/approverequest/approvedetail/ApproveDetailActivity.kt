package com.framgia.fbook.screen.approverequest.approvedetail;

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.ActivityApprovedetailBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.screen.SearchBook.SearchBookActivity
import com.framgia.fbook.screen.onItemRecyclerViewClickListener
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * ApproveDetailActivity Screen.
 */
class ApproveDetailActivity : BaseActivity(), ApproveDetailContract.ViewModel, onItemRecyclerViewClickListener {

  @Inject
  internal lateinit var mPresenter: ApproveDetailContract.Presenter
  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mApproveDetailAdapter: ApproveDetailAdapter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mApproveDetailOwnerAdapter: ApproveDetailOwnerAdapter
  private lateinit var mApproveDetailComponent: ApproveDetailComponent
  private lateinit var mGetListUserListener: GetListUserListener

  val mBook: ObservableField<Book> = ObservableField()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mApproveDetailComponent = DaggerApproveDetailComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .approveDetailModule(ApproveDetailModule(this))
        .build()
    mApproveDetailComponent.inject(this)

    val binding = DataBindingUtil.setContentView<ActivityApprovedetailBinding>(this,
        R.layout.activity_approvedetail)
    binding.viewModel = this
    val bookId = intent.getIntExtra(Constant.BOOK_DETAIL_EXTRA, 0)
    if (bookId != 0) {
      mPresenter.getApproveDetail(bookId)
    }
    mApproveDetailOwnerAdapter.setOnItemRecyclerViewClickListener(this)
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  fun setGetListUserListener(getListUserListener: GetListUserListener) {
    mGetListUserListener = getListUserListener
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onError(e: BaseException) {
    mDialogManager.dialogError(e.getMessageError())
  }

  override fun onItemClickListener(any: Any?) {
    //TODO edit later
  }

  override fun onGetApproveDetailSuccess(book: Book?) {
    book?.let {
      mBook.set(book)
      mApproveDetailOwnerAdapter.updateData(it.owners)
      mGetListUserListener.onGetListUserWaiting(it.usersWaitings)
      mGetListUserListener.onGetListUserReading(it.usersReadings)
      mGetListUserListener.onGetListUserReturning(it.usersReturnings)
      mGetListUserListener.onGetListUserReturned(it.usersReturneds)
    }
  }

  fun getApproveRequestComponent(): ApproveDetailComponent {
    return mApproveDetailComponent
  }

  fun onClickArrowBack() {
    mNavigator.finishActivity()
  }

  fun onClickSearch() {
    mNavigator.startActivity(SearchBookActivity::class.java)
  }
}
