package com.framgia.fbook.screen.mainpage

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.FragmentMainPageBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.bookdetail.BookDetailActivity
import com.framgia.fbook.screen.listbookseemore.ListBookFragment
import com.framgia.fbook.screen.main.MainActivity
import com.framgia.fbook.screen.mainpage.adapter.MainPageAdapter
import com.framgia.fbook.screen.onItemRecyclerViewClickListener
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.NavigateAnim
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject
import javax.inject.Named

/**
 * MainPage Screen.
 */
open class MainPageFragment : BaseFragment(), MainPageContract.ViewModel,
    onItemRecyclerViewClickListener, MainActivity.ListBookMainPageListener {
  @Inject
  internal lateinit var mPresenter: MainPageContract.Presenter
  @Inject
  lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mUserRepository: UserRepository

  @field:[Inject Named("AdapterLate")]
  lateinit var mMainPageAdapterLateBook: MainPageAdapter
  @field:[Inject Named("AdapterRating")]
  lateinit var mMainPageAdapterRatingBook: MainPageAdapter
  @field:[Inject Named("AdapterView")]
  lateinit var mMainPageAdapterViewBook: MainPageAdapter
  @field:[Inject Named("AdapterWaiting")]
  lateinit var mMainPageAdapterWaitingBook: MainPageAdapter
  @field:[Inject Named("AdapterRead")]
  lateinit var mMainPageAdapterReadBook: MainPageAdapter
  @Inject
  lateinit var mNavigator: Navigator
  private var mOfficeId: Int? = null
  private lateinit var mBinding: FragmentMainPageBinding
  val mVisibleLayoutBook: ObservableField<Boolean> = ObservableField(false)

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    DaggerMainPageComponent.builder()
        .mainComponent((activity as MainActivity).getMainComponent())
        .mainPageModule(MainPageModule(this))
        .build()
        .inject(this)

    mBinding = DataBindingUtil.inflate<FragmentMainPageBinding>(inflater,
        R.layout.fragment_main_page, container, false)
    mBinding.viewModel = this
    initData()
    return mBinding.root
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is MainActivity) {
      context.setListBookMainPageListener(this)
    }
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onError(error: BaseException) {
    mDialogManager.showSnackBarTitleString(mBinding.contentMainPage, error.getMessageError())
  }

  override fun onGetOfficeSuccess(listOffice: List<Office>?) {
    listOffice?.let {
      //TODO edit later
    }
  }

  override fun onGetSectionListBookSuccess(typeBook: Int, listBook: List<Book>?) {
    when (typeBook) {
      TypeBook.LATE_BOOK -> mMainPageAdapterLateBook.updateData(listBook)
      TypeBook.RATING_BOOK -> mMainPageAdapterRatingBook.updateData(listBook)
      TypeBook.VIEW_BOOK -> mMainPageAdapterViewBook.updateData(listBook)
      TypeBook.WAITING_BOOK -> mMainPageAdapterWaitingBook.updateData(listBook)
    //when to final adapter
      TypeBook.READ_BOOK -> {
        mMainPageAdapterReadBook.updateData(listBook)
        mMainPageAdapterLateBook.notifyDataSetChanged()
        mMainPageAdapterViewBook.notifyDataSetChanged()
        mMainPageAdapterRatingBook.notifyDataSetChanged()
        mMainPageAdapterWaitingBook.notifyDataSetChanged()
        mMainPageAdapterReadBook.notifyDataSetChanged()
      }
    }
    mVisibleLayoutBook.set(true)
  }

  override fun onGetListBook(officeId: Int?) {
    mOfficeId = officeId
    mPresenter.getSectionListBook(officeId)
  }

  private fun initData() {
    mOfficeId = (activity as MainActivity).getCurrentOfficeId()
    mPresenter.getSectionListBook(mOfficeId)
    mMainPageAdapterLateBook.setItemInternalBookListener(this)
    mMainPageAdapterRatingBook.setItemInternalBookListener(this)
    mMainPageAdapterViewBook.setItemInternalBookListener(this)
    mMainPageAdapterWaitingBook.setItemInternalBookListener(this)
    mMainPageAdapterReadBook.setItemInternalBookListener(this)
  }

  override fun onItemClickListener(any: Any?) {
    val bundle = Bundle()
    if (any is Book) {
      any.id?.let {
        bundle.putInt(Constant.BOOK_DETAIL_EXTRA, it)
      }
    }
    mNavigator.startActivity(BookDetailActivity::class.java, bundle)
  }

  fun onClickMoreLateBook() {
    mNavigator.goNextChildFragment(R.id.layout_content_main,
        ListBookFragment.newInstance(Constant.LATE, mOfficeId), true,
        NavigateAnim.NONE, ListBookFragment.TAG)
  }

  fun onClickMoreViewBook() {
    mNavigator.goNextChildFragment(R.id.layout_content_main,
        ListBookFragment.newInstance(Constant.VIEW, mOfficeId), true,
        NavigateAnim.NONE, ListBookFragment.TAG)
  }

  fun onClickMoreRatingBook() {
    mNavigator.goNextChildFragment(R.id.layout_content_main,
        ListBookFragment.newInstance(Constant.RATING, mOfficeId), true,
        NavigateAnim.NONE, ListBookFragment.TAG)
  }

  fun onClickMoreWaitingBook() {
    mNavigator.goNextChildFragment(R.id.layout_content_main,
        ListBookFragment.newInstance(Constant.WAITING, mOfficeId), true,
        NavigateAnim.NONE, ListBookFragment.TAG)
  }

  fun onClickMoreReadBook() {
    mNavigator.goNextChildFragment(R.id.layout_content_main,
        ListBookFragment.newInstance(Constant.READ, mOfficeId), true,
        NavigateAnim.NONE, ListBookFragment.TAG)
  }

  companion object {

    val TAG: String = MainPageFragment::class.java.name

    fun newInstance(): MainPageFragment {
      return MainPageFragment()
    }
  }
}
