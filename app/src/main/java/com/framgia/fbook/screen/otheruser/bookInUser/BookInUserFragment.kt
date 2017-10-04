package com.framgia.fbook.screen.otheruser.bookInUser

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.FragmentBookInUserBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.otheruser.OtherUserActivity
import com.framgia.fbook.utils.navigator.Navigator
import javax.inject.Inject

/**
 * BookInUser Screen.
 */
class BookInUserFragment : BaseFragment(), BookInUserContract.ViewModel {

  companion object {
    val TAG: String? = BookInUserFragment::class.java.name
    private val BOOK_IN_USER_PROFILE: String = "BOOK_IN_USER_PROFILE"
    fun newInstance(type: String?): BookInUserFragment {
      val fragment = BookInUserFragment()
      val bundle = Bundle()
      bundle.putString(BOOK_IN_USER_PROFILE, type)
      fragment.arguments = bundle
      return fragment
    }
  }

  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mUserRepository: UserRepository
  @Inject
  internal lateinit var mPresenter: BookInUserContract.Presenter
  private var mBook: ObservableField<Book> = ObservableField()

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerBookInUserComponent.builder()
        .otherUserComponent((activity as OtherUserActivity).getOtherUserComponent())
        .bookInUserModule(BookInUserModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentBookInUserBinding>(inflater,
        R.layout.fragment_book_in_user, container, false)
    binding.viewModel = this
    fillData()
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

  override fun onGetBookInUserProfileSuccess(book: List<Book>?) {
    //Todo Edit Later
  }

  override fun onError(exception: BaseException) {
    Log.d(TAG, exception.getMessageError())
  }

  fun fillData() {
    //Todo edit later
    val typeRequest: String? = arguments.getString(BOOK_IN_USER_PROFILE)
    mPresenter.getBookInUserProfile(mUserRepository.getUserLocal()?.id, typeRequest)
  }
}
