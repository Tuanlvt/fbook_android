package com.framgia.fbook.screen.otheruser;

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.databinding.ActivityOtherUserBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.utils.navigator.Navigator
import javax.inject.Inject

/**
 * OtherUser Screen.
 */
class OtherUserActivity : BaseActivity(), OtherUserContract.ViewModel {

  private val mNavigator: Navigator by lazy { Navigator(this) }
  @Inject
  internal lateinit var presenter: OtherUserContract.Presenter
  @Inject
  internal lateinit var mAdapter: OtherUserAdapter
  private lateinit var mOtherUserComponent: OtherUserComponent
  val mPageLimit: ObservableField<Int> = ObservableField(5)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mOtherUserComponent = DaggerOtherUserComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .otherUserModule(OtherUserModule(this))
        .build()
    mOtherUserComponent.inject(this)

    val binding = DataBindingUtil.setContentView<ActivityOtherUserBinding>(this,
        R.layout.activity_other_user)
    binding.viewModel = this
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
    fun newInstance(userId: Int): OtherUserActivity {
      val bundle = Bundle()
      bundle.getInt(userId.toString())
      return OtherUserActivity()
    }
  }
}
