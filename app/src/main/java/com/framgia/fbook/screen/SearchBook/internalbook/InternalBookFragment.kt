package com.framgia.fbook.screen.SearchBook.internalbook

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.FragmentInternalbookBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.SearchBook.SearchBookActivity
import com.framgia.fbook.screen.SearchBook.TypeSearch
import com.framgia.fbook.screen.SearchBook.adaptersearchbook.SearchBookAdapter
import com.framgia.fbook.screen.bookdetail.BookDetailActivity
import com.framgia.fbook.screen.onItemRecyclerViewClickListener
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.common.StringUtils
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * InternalBook Screen.
 */
open class InternalBookFragment : BaseFragment(), InternalBookContract.ViewModel, onItemRecyclerViewClickListener {

  companion object {
    val TAG: String = InternalBookFragment::class.java.name

    fun newInstance(): InternalBookFragment {
      return InternalBookFragment()
    }
  }

  @Inject
  internal lateinit var mPresenter: InternalBookContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mInternalBookAdapter: SearchBookAdapter
  @Inject
  internal lateinit var mNavigator: Navigator
  var mKeyWord: ObservableField<String> = ObservableField()
  var mKeyWordErrorMsg: ObservableField<String> = ObservableField()
  private var mTypeSearch: String? = null
  var mIsTitle: ObservableField<Boolean> = ObservableField()
  var mIsAuthor: ObservableField<Boolean> = ObservableField()
  var mIsDescription: ObservableField<Boolean> = ObservableField()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerInternalBookComponent.builder()
        .searchBookComponent((activity as SearchBookActivity).getSearchBookComponent())
        .internalBookModule(InternalBookModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentInternalbookBinding>(inflater,
        R.layout.fragment_internalbook, container,
        false)
    binding.viewModel = this
    mTypeSearch = context.getString(R.string.title)
    mIsTitle.set(true)
    mInternalBookAdapter.setItemInternalBookListener(this)
    return binding.root
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    super.onStop()
    mPresenter.onStop()
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onError(e: BaseException) {
    mDialogManager.dialogError(e.getMessageError())
  }

  override fun onItemClickListener(any: Any?) {
    any?.let {
      val bundle = Bundle()
      bundle.putParcelable(Constant.BOOK_DETAIL_EXTRA, any as Book)
      mNavigator.startActivity(BookDetailActivity::class.java, bundle)
    }
  }

  override fun onSearchBookSuccess(listBook: List<Book>?) {
    listBook?.let { mInternalBookAdapter.updateData(it, TypeSearch.INTERNAL_BOOK) }
  }

  fun onClickSearch() {
    if (StringUtils.isBlank(mKeyWord.get())) {
      mKeyWordErrorMsg.set(context.getString(R.string.is_empty))
      return
    }
    mPresenter.searchBook(mKeyWord.get(), mTypeSearch)
  }

  fun onRadioSearchTitle() {
    mIsTitle.set(true)
    mIsAuthor.set(false)
    mIsDescription.set(false)
    mTypeSearch = context.getString(R.string.title)

  }

  fun onRadioSearchAuthor() {
    mIsTitle.set(false)
    mIsAuthor.set(true)
    mIsDescription.set(false)
    mTypeSearch = context.getString(R.string.author)
  }

  fun onRadioSearchDescription() {
    mIsTitle.set(false)
    mIsAuthor.set(false)
    mIsDescription.set(true)
    mTypeSearch = context.getString(R.string.description)
  }
}
