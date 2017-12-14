package com.framgia.fbook.screen.updatebook;

import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.MenuItem
import android.widget.DatePicker
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.EditBookRequest
import com.framgia.fbook.databinding.ActivityUpdateBookBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.common.DateTimeUtils
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.widget.dialog.DialogManager
import java.util.*
import javax.inject.Inject


/**
 * UpdateBook Screen.
 */
class UpdateBookActivity : BaseActivity(), UpdateBookContract.ViewModel, DatePickerDialog.OnDateSetListener {

  @Inject
  internal lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mPresenter: UpdateBookContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager

  private val mListCategory = mutableListOf<Category>()
  private val mListOffice = mutableListOf<Office>()
  private var mCurrentOfficePosition: Int = 0
  private var mCurrentCategoryPosition: Int = 0
  private var mCategoryId: Int? = 0
  private var mOfficeId: Int? = 0
  private lateinit var mBinding: ActivityUpdateBookBinding
  private var mEditBookRequest: ObservableField<EditBookRequest> = ObservableField()

  var mBook = Book()
  val titleError: ObservableField<String> = ObservableField()
  val authorError: ObservableField<String> = ObservableField()
  val descriptionError: ObservableField<String> = ObservableField()
  val mPublishDate: ObservableField<String> = ObservableField()
  val mCurrentOffice: ObservableField<String> = ObservableField()
  val mCurrentCategory: ObservableField<String> = ObservableField()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setUpActionBar(true, R.string.form_edit_book_to_admin)

    DaggerUpdateBookComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .updateBookModule(UpdateBookModule(this))
        .build()
        .inject(this)

    mBinding = DataBindingUtil.setContentView<ActivityUpdateBookBinding>(this,
        R.layout.activity_update_book)

    mBinding.viewModel = this
    initData()
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> finish()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onInputTitleError(errorMsg: String?) {
    titleError.set(errorMsg)
  }

  override fun onInputAuthorError(errorMsg: String?) {
    authorError.set(errorMsg)
  }

  override fun onInputDescriptionError(errorMsg: String?) {
    descriptionError.set(errorMsg)
  }

  override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
    if (year == 0) {
      updateDatePublish(mBook.publishDate)
      return
    }
    val date = DateTimeUtils.getDateStringFromDayMonthYear(year, month, day,
        getString(R.string.date_format_yyyy_MM_dd))
    date?.let { updateDatePublish(it) }
  }

  override fun onError(exception: BaseException) {
    mDialogManager.showSnackBarTitleString(mBinding.buttonCreateForm, exception.getMessageError())
  }

  override fun onGetOfficeSuccess(listOffice: List<Office>?) {
    listOffice?.let {
      mListOffice.addAll(it)
      mCurrentOfficePosition = mListOffice.indices.first { mListOffice[it].id == mOfficeId }
      updateCurrentOffice()
    }
  }

  override fun onGetCategorySuccess(listCategory: List<Category>?) {
    listCategory?.let {
      mListCategory.addAll(it)
      mCurrentCategoryPosition = mListCategory.indices.first { mListCategory[it].id == mCategoryId }
      updateCurrentCategory()
    }
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onHideProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onRequestFormEditBookSuccess() {
    mNavigator.finishActivity()
  }

  private fun initData() {
    mBook = intent.extras.getParcelable(Constant.BOOK_UPDATE_EXTRA)
    mPublishDate.set(mBook.publishDate)
    mDialogManager.dialogDatePicker(this, Calendar.getInstance())
    mPresenter.getData()
    mCategoryId = mBook.category?.id
    mOfficeId = mBook.office?.id
  }

  private fun updateDatePublish(date: String?) {
    mPublishDate.set(date)
  }

  fun onClickSendRequest() {
    val editBookRequest = EditBookRequest().apply {
      title = mBook.title
      publishDate = mPublishDate.get()
      description = mBook.description
      author = mBook.author
      officeId = mOfficeId
      categoryId = mCategoryId
      codeBook = mBook.codeBook
    }
    mEditBookRequest.set(editBookRequest)
    if (!mPresenter.validateDataInput(mEditBookRequest.get())) {
      return
    }
    mPresenter.sendFormEditBook(mBook.id, mEditBookRequest.get())
  }

  fun onPickDatePublish() {
    mDialogManager.showDatePickerDialog()
  }


  fun onPickOffice() {
    val workSpace: MutableList<String?> = mutableListOf()
    mListOffice.mapTo(workSpace) { it.name }
    mDialogManager.dialogListSingleChoice(getString(R.string.select_office), workSpace,
        mCurrentOfficePosition,
        MaterialDialog.ListCallbackSingleChoice { _, _, position, _ ->
          mOfficeId = mListCategory[position].id
          mCurrentOfficePosition = position
          updateCurrentOffice()
          true
        })
  }

  fun onPickCategory() {
    val listCategory: MutableList<String?> = mutableListOf()
    mListCategory.mapTo(listCategory) { it.name }
    mDialogManager.dialogListSingleChoice(getString(R.string.select_category), listCategory,
        mCurrentCategoryPosition,
        MaterialDialog.ListCallbackSingleChoice { _, _, position, _ ->
          mCategoryId = mListCategory[position].id
          mCurrentCategoryPosition = position
          updateCurrentCategory()
          true
        })
  }

  private fun updateCurrentOffice() {
    mCurrentOffice.set(mListOffice[mCurrentOfficePosition].name)
  }

  private fun updateCurrentCategory() {
    mCurrentCategory.set(mListCategory[mCurrentCategoryPosition].name)
  }
}
