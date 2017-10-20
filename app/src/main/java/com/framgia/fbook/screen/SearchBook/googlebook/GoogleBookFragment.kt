package com.framgia.fbook.screen.SearchBook.googlebook

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.framgia.fbook.R
import com.framgia.fbook.data.model.GoogleBook
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.databinding.FragmentGooglebookBinding
import com.framgia.fbook.screen.BaseFragment
import com.framgia.fbook.screen.SearchBook.SearchBookActivity
import com.framgia.fbook.screen.SearchBook.TypeSearch
import com.framgia.fbook.screen.SearchBook.adaptersearchbook.SearchBookAdapter
import com.framgia.fbook.screen.onItemRecyclerViewClickListener
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.common.StringUtils
import com.fstyle.structure_android.widget.dialog.DialogManager
import javax.inject.Inject

/**
 * GoogleBook Screen.
 */
open class GoogleBookFragment : BaseFragment(), GoogleBookContract.ViewModel, onItemRecyclerViewClickListener {

  @Inject
  internal lateinit var mPresenter: GoogleBookContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mGoogleBookAdapter: SearchBookAdapter
  var mBookName: ObservableField<String> = ObservableField()
  var mErrorMsg: ObservableField<String> = ObservableField()

  companion object {

    val TAG: String = "GoogleBookFragment"

    fun newInstance(): GoogleBookFragment {
      return GoogleBookFragment()
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    DaggerGoogleBookComponent.builder()
        .searchBookComponent((activity as SearchBookActivity).getSearchBookComponent())
        .googleBookModule(GoogleBookModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.inflate<FragmentGooglebookBinding>(inflater,
        R.layout.fragment_googlebook, container, false)
    binding.viewModel = this
    mGoogleBookAdapter.setItemInternalBookListener(this)
    binding.editTextSearch.setOnEditorActionListener { v, actionId, event ->
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        onKeySearch()
      }
      true
    }
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

  override fun onSearchBookSuccess(bookList: List<GoogleBook>?) {
    bookList?.let { mGoogleBookAdapter.updateDataGoogleBook(it, TypeSearch.GOOGLE_BOOK) }
  }

  fun ResetTextOnClick(view: View) {
    mBookName.set(Constant.EXTRA_EMTY)
  }

  override fun onItemClickListener(any: Any?) {
    //TODO edit later
  }

  private fun onKeySearch() {
    if (StringUtils.isBlank(mBookName.get())) {
      mErrorMsg.set(context.getString(R.string.is_empty))
      return
    }
    mPresenter.searchBook(mBookName.get())
  }

  fun onClickSearchBookGoogle(view: View) {
    if (StringUtils.isBlank(mBookName.get())) {
      mErrorMsg.set(context.getString(R.string.is_empty))
      return
    }
    mPresenter.searchBook(mBookName.get())
  }
}
