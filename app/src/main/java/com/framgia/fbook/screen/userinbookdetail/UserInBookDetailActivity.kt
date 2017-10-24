package com.framgia.fbook.screen.userinbookdetail;

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.View
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.databinding.ActivityUserInBookDetailBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.screen.SearchBook.SearchBookActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import javax.inject.Inject


/**
 * UserInBookDetail Screen.
 */
open class UserInBookDetailActivity : BaseActivity(), UserInBookDetailContract.ViewModel {

  @Inject
  internal lateinit var mPresenter: UserInBookDetailContract.Presenter
  @Inject
  internal lateinit var mUserRepository: UserRepository
  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mUserInBookDetailAdapter: UserInBookDetailAdapter
  lateinit var mUserInBookDetailComponent: UserInBookDetailComponent

  val mBook: ObservableField<Book> = ObservableField()
  val mPageLimit: ObservableField<Int> = ObservableField(4)
  val mCurrentPage: ObservableField<Int> = ObservableField()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val book: Book = intent.getParcelableExtra(Constant.USER_BOOK_DETAIL_EXTRA)
    val page = intent.getIntExtra(Constant.USER_BOOK_DETAIL_PAGE_EXTRA, 0)

    mUserInBookDetailComponent = DaggerUserInBookDetailComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .userInBookDetailModule(UserInBookDetailModule(this, book))
        .build()
    mUserInBookDetailComponent.inject(this)

    val binding = DataBindingUtil.setContentView<ActivityUserInBookDetailBinding>(this,
        R.layout.activity_user_in_book_detail)
    binding.viewModel = this

    mBook.set(book)
    mCurrentPage.set(page)
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onBackPressed() {
    mNavigator.finishActivityWithResult(Activity.RESULT_OK)
    super.onBackPressed()
  }

  fun getUserInBookDetailComponent(): UserInBookDetailComponent {
    return mUserInBookDetailComponent
  }

  fun onClickArrowBack(view: View) {
    mNavigator.finishActivityWithResult(Activity.RESULT_OK)
  }

  fun onClickSearch(view: View) {
    mNavigator.startActivity(SearchBookActivity::class.java)
  }
}
