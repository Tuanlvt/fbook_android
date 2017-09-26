package com.framgia.fbook.screen.mainpage

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Office
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
open class MainPageFragment : BaseFragment(), MainPageContract.ViewModel, onItemRecyclerViewClickListener {

  @Inject
  internal lateinit var mPresenter: MainPageContract.Presenter
  @Inject
  lateinit var mDialogManager: DialogManager

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

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    DaggerMainPageComponent.builder()
        .mainComponent((activity as MainActivity).getMainComponent())
        .mainPageModule(MainPageModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentMainPageBinding>(inflater,
        R.layout.fragment_main_page, container, false)
    binding.viewModel = this
    mPresenter.getSectionListBook()
    mMainPageAdapterLateBook.setItemInternalBookListener(this)
    mMainPageAdapterRatingBook.setItemInternalBookListener(this)
    mMainPageAdapterViewBook.setItemInternalBookListener(this)
    mMainPageAdapterWaitingBook.setItemInternalBookListener(this)
    mMainPageAdapterReadBook.setItemInternalBookListener(this)
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

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onError(error: BaseException) {
    mDialogManager.dialogError(error.getMessageError())
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
  }

  override fun onItemClickListener(any: Any?) {
    val bundle = Bundle()
    bundle.putParcelable(Constant.BOOK_DETAIL_EXTRA, any as Book)
    mNavigator.startActivity(BookDetailActivity::class.java, bundle)
  }

  fun onClickMoreLateBook() {
    mNavigator.goNextChildFragment(R.id.contentMainPage,
        ListBookFragment.newInstance(Constant.LATE), true,
        NavigateAnim.NONE, ListBookFragment.TAG)
  }

  fun onClickMoreViewBook() {
    mNavigator.goNextChildFragment(R.id.contentMainPage,
        ListBookFragment.newInstance(Constant.VIEW), true,
        NavigateAnim.NONE, ListBookFragment.TAG)
  }

  fun onClickMoreRatingBook() {
    mNavigator.goNextChildFragment(R.id.contentMainPage,
        ListBookFragment.newInstance(Constant.RATING), true,
        NavigateAnim.NONE, ListBookFragment.TAG)
  }

  fun onClickMoreWaitingBook() {
    mNavigator.goNextChildFragment(R.id.contentMainPage,
        ListBookFragment.newInstance(Constant.WAITING), true,
        NavigateAnim.NONE, ListBookFragment.TAG)
  }

  fun onClickMoreReadBook() {
    mNavigator.goNextChildFragment(R.id.contentMainPage,
        ListBookFragment.newInstance(Constant.READ), true,
        NavigateAnim.NONE, ListBookFragment.TAG)
  }

  companion object {

    val TAG: String = MainPageFragment::class.java.name

    fun newInstance(): MainPageFragment {
      return MainPageFragment()
    }
  }
}
