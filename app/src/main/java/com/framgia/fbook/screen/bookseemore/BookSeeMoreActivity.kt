package com.framgia.fbook.screen.bookseemore;

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.Sort
import com.framgia.fbook.data.model.SortBook
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.ActivityBookSeeMoreBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.screen.EndlessRecyclerOnScrollListener
import com.framgia.fbook.screen.bookdetail.BookDetailActivity
import com.framgia.fbook.screen.bookseemore.adapter.BookSeeMoreAdapter
import com.framgia.fbook.screen.main.MainActivity
import com.framgia.fbook.screen.onItemRecyclerViewClickListener
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * BookSeeMore Screen.
 */
class BookSeeMoreActivity : BaseActivity(), BookSeeMoreContract.ViewModel, onItemRecyclerViewClickListener, MainActivity.ListBookSeeMoreListener {

  @Inject
  internal lateinit var mPresenter: BookSeeMoreContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mAdapter: BookSeeMoreAdapter
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
  private var mOfficeId: Int? = null
  val mShowProgress: ObservableField<Boolean> = ObservableField()
  val mCurrentCategory: ObservableField<String> = ObservableField()
  val mCurrentSortBy: ObservableField<String> = ObservableField()
  val mIsOrderByAsc: ObservableField<Boolean> = ObservableField()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerBookSeeMoreComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .bookSeeMoreModule(BookSeeMoreModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.setContentView<ActivityBookSeeMoreBinding>(this,
        R.layout.activity_book_see_more)
    binding.viewModel = this
    initData(binding)
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
      mAdapter.clearData()
    }
    listBook?.let {
      mAdapter.updateData(listBook)
    }
    mShowProgress.set(false)
  }

  override fun onGetListBook(officeId: Int?) {
    mOfficeId = officeId
    mIsLoadMore = false
    mPresenter.getListBook(mTypeBook, Constant.PAGE, officeId)
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

  private fun initData(binding: ActivityBookSeeMoreBinding) {


    mOfficeId = intent.getIntExtra(Constant.KEY_OFFICE, 0)
    mTypeBook = intent.getStringExtra(Constant.KEY_TYPE)
    mPresenter.getListCategory()
    mPresenter.getListSortBook()
    mPresenter.getListBook(mTypeBook, Constant.PAGE, mOfficeId)
    mAdapter.setItemInternalBookListener(this)
    mIsOrderByAsc.set(false)

    val gridLayoutManager = GridLayoutManager(applicationContext, 3)
    binding.recyclerListBook.layoutManager = gridLayoutManager
    binding.recyclerListBook.addOnScrollListener(
        object : EndlessRecyclerOnScrollListener(gridLayoutManager) {
          override fun onLoadMore(page: Int) {
            mIsLoadMore = true
            mIsShowProgressDialog = false
            mShowProgress.set(true)
            when (mTypeGetBook) {
              BOOK_NORMAL -> {
                mPresenter.getListBook(mTypeBook, page, mOfficeId)
              }
              BOOK_CATEGORY -> {
                mShowProgress.set(false)
                //TODO edit later
              }
              BOOK_SORT -> {
                mPresenter.getListBookBySort(mTypeBook, page, mSort, mOfficeId)
              }
            }
          }
        })
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
    mDialogManager.dialogListSingleChoice(applicationContext.getString(R.string.category),
        listCategory,
        mCurrentCategoryPosition,
        MaterialDialog.ListCallbackSingleChoice({ _, _, position, charSequence ->
          run {
            mCurrentCategoryPosition = position
            mCurrentCategory.set(charSequence.toString())
            mIsShowProgressDialog = true
            mIsLoadMore = false
            mPresenter.getListBookByCategory(mListCategory[position].id, mOfficeId)
            mTypeGetBook = BOOK_CATEGORY
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
    mDialogManager.dialogListSingleChoice(applicationContext.getString(R.string.sort_by),
        listSortBook,
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
            mIsLoadMore = false
            mPresenter.getListBookBySort(mTypeBook, Constant.PAGE, mSort, mOfficeId)
            mTypeGetBook = BOOK_SORT
            EndlessRecyclerOnScrollListener.resetLoadMore()
          }
          true
        }
    ))
  }

  fun onArrowClick(view: View) {
    mNavigator.finishActivity()
  }

  companion object {
    val TAG = BookSeeMoreActivity::class.java.simpleName
    private val DESC = "desc"
    private val ASC = "asc"
    private val BOOK_NORMAL = 0
    private val BOOK_CATEGORY = 1
    private val BOOK_SORT = 2

    fun newInstance(): BookSeeMoreActivity {
      return BookSeeMoreActivity()
    }
  }
}
