package com.framgia.fbook.screen.listbookseemore

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.Sort
import com.framgia.fbook.data.model.SortBook
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.FragmentListbookBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.EndlessRecyclerOnScrollListener
import com.framgia.fbook.screen.bookdetail.BookDetailActivity
import com.framgia.fbook.screen.listbookseemore.adapter.ListBookAdapter
import com.framgia.fbook.screen.main.MainActivity
import com.framgia.fbook.screen.onItemRecyclerViewClickListener
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * ListBook Screen.
 */
open class ListBookFragment : BaseFragment(), ListBookContract.ViewModel, onItemRecyclerViewClickListener {

  @Inject
  internal lateinit var mPresenter: ListBookContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mListBookAdapter: ListBookAdapter
  @Inject
  internal lateinit var mNavigator: Navigator
  private val mListCategory = mutableListOf<Category>()
  private val mListSortBook = mutableListOf<SortBook>()
  private var mCurrentCategoryPosition = 0
  private var mCurrentSortBookPosition = 0
  private var mIsLoadMore = false
  private var mTypeBook: String? = null
  private var mTypeGetBook: Int = 0
  private val mSort by lazy { Sort() }
  private var mIsShowProgressDialog = true
  val mShowProgress: ObservableField<Boolean> = ObservableField()
  val mCurrentCategory: ObservableField<String> = ObservableField()
  val mCurrentSortBy: ObservableField<String> = ObservableField()
  val mIsOrderByAsc: ObservableField<Boolean> = ObservableField()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerListBookComponent.builder()
        .mainComponent((activity as MainActivity).getMainComponent())
        .listBookModule(ListBookModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentListbookBinding>(inflater,
        R.layout.fragment_listbook, container, false)
    binding.viewModel = this
    mListBookAdapter.setItemInternalBookListener(this)
    mTypeBook = arguments.getString(Constant.LIST_BOOK_EXTRA)
    mPresenter.getListBook(mTypeBook, Constant.PAGE)
    mPresenter.getListCategory()
    mPresenter.getListSortBook()
    mIsOrderByAsc.set(false)
    val gridLayoutManager = GridLayoutManager(context, 3)
    binding.recyclerListBook.layoutManager = gridLayoutManager
    binding.recyclerListBook.addOnScrollListener(
        object : EndlessRecyclerOnScrollListener(gridLayoutManager) {
          override fun onLoadMore(page: Int) {
            mIsLoadMore = true
            mIsShowProgressDialog = false
            mShowProgress.set(true)
            when (mTypeGetBook) {
              BOOK_NORMAL -> {
                mPresenter.getListBook(mTypeBook, page)
              }
              BOOK_CATEGORY -> {
                //TODO edit later
              }
              BOOK_SORT -> {
                mPresenter.getListBookBySort(mTypeBook, page, mSort)
              }
            }

          }
        })

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

  override fun onDismissProgressBarDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onShowProgressBarDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun isShowProgressDialog(): Boolean {
    return mIsShowProgressDialog
  }

  override fun onError(exception: BaseException) {
    mDialogManager.dialogError(exception.getMessageError())
    mShowProgress.set(false)
  }

  override fun onGetListCategorySuccess(listCategory: List<Category>?) {
    listCategory?.let { mListCategory.addAll(listCategory) }
  }

  override fun onGetListSortBookSuccess(listSort: List<SortBook>?) {
    listSort?.let {
      mListSortBook.addAll(it)
    }
  }

  override fun onGetListBookSuccess(listBook: List<Book>?) {
    if (!mIsLoadMore) {
      mListBookAdapter.clearData()
    }
    listBook?.let {
      mListBookAdapter.updateData(listBook)
    }
    mShowProgress.set(false)
  }

  override fun onItemClickListener(any: Any?) {
    val bundle = Bundle()
    bundle.putParcelable(Constant.BOOK_DETAIL_EXTRA, any as Book)
    mNavigator.startActivity(BookDetailActivity::class.java, bundle)
  }

  fun onClickOrderBy() {
    mIsOrderByAsc.set(!mIsOrderByAsc.get())
  }

  fun onClickChooseCategory() {
    if (mListCategory.isEmpty()) {
      return
    }
    val listCategory: MutableList<String?> = mutableListOf()
    mListCategory.indices.mapTo(listCategory) { mListCategory[it].name }
    mDialogManager.dialogListSingleChoice(context.getString(R.string.category), listCategory,
        mCurrentCategoryPosition,
        MaterialDialog.ListCallbackSingleChoice({ _, _, position, charSequence ->
          run {
            mCurrentCategoryPosition = position
            mCurrentCategory.set(charSequence.toString())
            mIsShowProgressDialog = true
            mPresenter.getListBookByCategory(mListCategory[position].id)
            mTypeGetBook = BOOK_CATEGORY
            mIsLoadMore = false
            EndlessRecyclerOnScrollListener.resetLoadMore()
          }
          true
        }))
  }

  fun onClickSortBy() {
    if (mListSortBook.isEmpty()) {
      return
    }
    val listSortBook: MutableList<String?> = mutableListOf()
    mListSortBook.indices.mapTo(listSortBook) { mListSortBook[it].text }
    mDialogManager.dialogListSingleChoice(context.getString(R.string.sort_by), listSortBook,
        mCurrentSortBookPosition, MaterialDialog.ListCallbackSingleChoice(
        { _, _, position, _ ->
          run {
            mCurrentSortBookPosition = position
            mCurrentSortBy.set(mListSortBook[position].text)
            mSort.by = if (mIsOrderByAsc.get()) {
              ASC
            } else {
              DESC
            }
            mSort.orderBy = mListSortBook[position].field
            mIsShowProgressDialog = true
            mPresenter.getListBookBySort(mTypeBook, Constant.PAGE, mSort)
            mTypeGetBook = BOOK_SORT
            mIsLoadMore = false
            EndlessRecyclerOnScrollListener.resetLoadMore()
          }
          true
        }
    ))
  }

  companion object {

    val TAG = ListBookFragment::class.java.simpleName
    private val DESC = "desc"
    private val ASC = "asc"
    private val BOOK_NORMAL = 0
    private val BOOK_CATEGORY = 1
    private val BOOK_SORT = 2

    fun newInstance(typeBook: String): ListBookFragment {
      val listBookFragment = ListBookFragment()
      val bundle = Bundle()
      bundle.putString(Constant.LIST_BOOK_EXTRA, typeBook)
      listBookFragment.arguments = bundle
      return listBookFragment
    }
  }
}
