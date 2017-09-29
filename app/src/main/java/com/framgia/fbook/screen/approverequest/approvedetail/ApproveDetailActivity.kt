package com.framgia.fbook.screen.approverequest.approvedetail;

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.databinding.ActivityApprovedetailBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.screen.SearchBook.SearchBookActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import javax.inject.Inject

/**
 * ApproveDetailActivity Screen.
 */
class ApproveDetailActivity : BaseActivity(), ApproveDetailContract.ViewModel {

  @Inject
  internal lateinit var mPresenter: ApproveDetailContract.Presenter
  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mApproveDetailAdapter: ApproveDetailAdapter
  private lateinit var mApproveDetailComponent: ApproveDetailComponent

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
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
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
