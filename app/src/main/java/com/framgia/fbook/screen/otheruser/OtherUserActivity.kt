package com.framgia.fbook.screen.otheruser;

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.databinding.ActivityOtherUserBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import javax.inject.Inject

/**
 * OtherUser Screen.
 */
class OtherUserActivity : BaseActivity(), OtherUserContract.ViewModel {

  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var presenter: OtherUserContract.Presenter
  @Inject
  internal lateinit var mAdapter: OtherUserAdapter
  private lateinit var mViewPager: ViewPager
  private lateinit var mOtherUserComponent: OtherUserComponent
  val mPageLimit: ObservableField<Int> = ObservableField(4)
  val mCurrentPage: ObservableField<Int> = ObservableField()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val page = intent.getIntExtra(Constant.USER_BOOK_DETAIL_PAGE_EXTRA, 0)
    mOtherUserComponent = DaggerOtherUserComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .otherUserModule(OtherUserModule(this))
        .build()
    mOtherUserComponent.inject(this)

    val binding = DataBindingUtil.setContentView<ActivityOtherUserBinding>(this,
        R.layout.activity_other_user)
    binding.viewModel = this
    mViewPager = binding.viewpager
    mViewPager.postDelayed({ mCurrentPage.set(page) }, TIME_DELAY)
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun onStop() {
    presenter.onStop()
    super.onStop()
  }

  fun getOtherUserComponent(): OtherUserComponent {
    return mOtherUserComponent
  }

  fun onClickArrowBack() {
    mNavigator.finishActivity()
  }

  companion object {
    val TAG: String = OtherUserActivity::class.java.name
    val TIME_DELAY: Long = 100
    fun newInstance(): OtherUserActivity {
      return OtherUserActivity()
    }
  }
}
