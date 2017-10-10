package com.framgia.fbook.screen.userinbookdetail.screen.UserReview

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.ReviewBookRequest
import com.framgia.fbook.databinding.FragmentUserReviewBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.login.LoginActivity
import com.framgia.fbook.screen.profile.ProfileActivity
import com.framgia.fbook.screen.userinbookdetail.UserInBookDetailActivity
import com.framgia.fbook.screen.userinbookdetail.UserReview.ItemUserReviewClickListener
import com.framgia.fbook.screen.userinbookdetail.UserReview.UserReviewAdapter
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject


/**
 * UserReview Screen.
 */
class UserReviewFragment : BaseFragment(), UserReviewContract.ViewModel, ItemUserReviewClickListener {

  companion object {

    private val TAB_USER_REVIEW: String = "TAB_USER_REVIEW"

    fun newInstance(book: Book): UserReviewFragment {
      val fragment = UserReviewFragment()
      val bundle = Bundle()
      bundle.putParcelable(UserReviewFragment.TAB_USER_REVIEW, book)
      fragment.arguments = bundle
      return fragment
    }
  }

  @Inject
  internal lateinit var mPresenter: UserReviewContract.Presenter
  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mUserReviewAdapter: UserReviewAdapter
  @Inject
  internal lateinit var mUserRepository: UserRepository

  private var mBookId: Int? = null
  private var mReviewBookRequest = ReviewBookRequest()
  val mAverageRating: ObservableField<Float> = ObservableField()
  val mUserComment: ObservableField<String> = ObservableField()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerUserReviewComponent.builder()
        .userInBookDetailComponent(
            (activity as UserInBookDetailActivity).getUserInBookDetailComponent())
        .userReviewModule(UserReviewModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentUserReviewBinding>(inflater,
        R.layout.fragment_user_review, container,
        false)
    binding.viewModel = this
    initData()
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

  override fun onGetBookDetailSuccess(book: Book?) {
    book?.reviewDetails?.let { mUserReviewAdapter.updateData(it) }
  }

  override fun onReviewBookSuccess() {
    mPresenter.getBookDetail(mBookId)
    mAverageRating.set(0F)
    mUserComment.set(null)
  }

  override fun onError(e: BaseException) {
    mDialogManager.dialogError(e.getMessageError())
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onItemUserReviewClick(reviewDetail: Book.ReviewDetail?) {
    val bundle = Bundle()
    reviewDetail?.user?.id?.let { it -> bundle.putInt(Constant.BOOK_DETAIL_EXTRA, it) }
    mNavigator.startActivity(ProfileActivity::class.java, bundle)
  }

  private fun initData() {
    val book: Book? = arguments.getParcelable(UserReviewFragment.TAB_USER_REVIEW)
    mBookId = book?.id
    mUserReviewAdapter.updateData(book?.reviewDetails)
    mUserReviewAdapter.setItemUserClickListener(this)
  }

  private fun isUserNotLoggedIn(): Boolean {
    if (mUserRepository.getUserLocal() == null) {
      showDialogInformUserNotLogin()
      return true
    }
    return false
  }

  private fun showDialogInformUserNotLogin() {
    mDialogManager.dialogBasic(getString(R.string.inform),
        getString(R.string.you_must_be_login_into_perform_this_function),
        MaterialDialog.SingleButtonCallback { _, _ ->
          mNavigator.startActivity(LoginActivity::class.java)
        })
  }

  fun getRatingBarChangeListener(): RatingBar.OnRatingBarChangeListener {
    return RatingBar.OnRatingBarChangeListener { _, rating, _ ->
      mAverageRating.set(rating)
    }
  }

  fun onClickSubmit(view: View) {
    if (isUserNotLoggedIn()) {
      return
    }
    mReviewBookRequest.reviewBook.star = mAverageRating.get()
    mReviewBookRequest.reviewBook.content = mUserComment.get()
    mPresenter.reviewBook(mBookId, mReviewBookRequest)
  }
}
