package com.framgia.fbook.screen.sharebook

import android.app.DatePickerDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.widget.DatePicker
import com.framgia.fbook.MainApplication
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.Category
import com.framgia.fbook.data.model.GoogleBook
import com.framgia.fbook.data.model.Office
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.BookRequest
import com.framgia.fbook.databinding.ActivitySharebookBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.utils.common.DateTimeUtils
import com.framgia.fbook.utils.common.StringUtils
import com.framgia.fbook.utils.navigator.Navigator
import com.fstyle.library.MaterialDialog
import com.fstyle.structure_android.widget.dialog.DialogManager
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.util.*
import javax.inject.Inject


/**
 * ShareBook Screen.
 */
open class ShareBookActivity : BaseActivity(), ShareBookContract.ViewModel, ItemImageSelectedListener, DatePickerDialog.OnDateSetListener {
  @Inject
  internal lateinit var mPresenter: ShareBookContract.Presenter
  @Inject
  lateinit var mNavigator: Navigator
  @Inject
  internal lateinit var mDialogManager: DialogManager
  @Inject
  internal lateinit var mImageSelectedAdapter: ImageSelectedAdapter
  val mBookRequest: ObservableField<BookRequest> = ObservableField()
  private val mListOffice = mutableListOf<Office>()
  private val mListCategory = mutableListOf<Category>()

  val titleError: ObservableField<String> = ObservableField()
  val authorError: ObservableField<String> = ObservableField()
  val descriptionError: ObservableField<String> = ObservableField()
  val mPublishDate: ObservableField<String> = ObservableField()
  val mCurrentOffice: ObservableField<String> = ObservableField()
  val mCurrentCategory: ObservableField<String> = ObservableField()
  private var mCurrentOfficePosition: Int = 0
  private var mCurrentCategoryPosition: Int = 0
  private lateinit var mBinding: ActivitySharebookBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    DaggerShareBookComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .shareBookModule(ShareBookModule(this))
        .build()
        .inject(this)

    mBinding = DataBindingUtil.setContentView<ActivitySharebookBinding>(this,
        R.layout.activity_sharebook)
    mBinding.viewModel = this

    mDialogManager.dialogDatePicker(this, Calendar.getInstance())
    EasyImage.configuration(this).setAllowMultiplePickInGallery(true)
    mImageSelectedAdapter.setItemImageSelectedListener(this)
    mBookRequest.set(BookRequest())
    mBookRequest.get().listImage = mImageSelectedAdapter.mListImage
    mPresenter.getData()
  }

  override fun onInputAuthorError(errorMsg: String?) {
    authorError.set(errorMsg)
  }

  override fun onInputDescriptionError(errorMsg: String?) {
    descriptionError.set(errorMsg)
  }

  override fun onInputTitleError(errorMsg: String?) {
    titleError.set(errorMsg)
  }

  override fun onStart() {
    super.onStart()
    mPresenter.onStart()
  }

  override fun onStop() {
    mPresenter.onStop()
    super.onStop()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
      override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
      }

      override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource,
          type: Int) {
        updateDataImage(imagesFiles)
      }
    })
  }

  override fun onRemoveSelected(image: BookRequest.Image) {
    mImageSelectedAdapter.removeOneItem(image)
  }

  override fun onShowProgressDialog() {
    mDialogManager.showIndeterminateProgressDialog()
  }

  override fun onDismissProgressDialog() {
    mDialogManager.dismissProgressDialog()
  }

  override fun onGetOfficeSuccess(listOffice: List<Office>?) {
    listOffice?.let {
      mListOffice.addAll(it)
      updateCurrentOffice(mCurrentOfficePosition)
    }
  }

  override fun onGetCategorySuccess(listCategory: List<Category>?) {
    listCategory?.let {
      mListCategory.addAll(it)
      updateCurrentCategory(mCurrentCategoryPosition)
    }
  }

  override fun onAddBookSuccess(book: Book?) {
    showSnackBar(R.string.add_successful)
    finish()
  }

  override fun onSearchBookFromInternalSuccess(listBook: List<Book>?) {
    val listBookString: MutableList<String?> = mutableListOf()
    listBook?.mapTo(listBookString) { it.title }
    if (listBook?.size != 0) {
      mDialogManager.dialogListSingleChoice(getString(R.string.import_data_from_internal_book),
          listBookString, 0,
          MaterialDialog.ListCallbackSingleChoice { _, _, position, _ ->
            updateFromInternalBook(listBook?.get(position))
            true
          })
    } else {
      showSnackBar(R.string.book_not_found)
    }
  }

  override fun onSearchBookFromGoogleBookSuccess(listGoogleBook: List<GoogleBook>?) {
    val listBookString: MutableList<String?> = mutableListOf()
    listGoogleBook?.mapTo(listBookString) { it.volumeInfo?.title }
    if (listGoogleBook?.size != 0) {
      mDialogManager.dialogListSingleChoice(getString(R.string.import_data_from_google_book),
          listBookString, 0,
          MaterialDialog.ListCallbackSingleChoice { _, _, position, _ ->
            updateFromGoogleBook(listGoogleBook?.get(position))
            true
          })
    } else {
      showSnackBar(R.string.book_not_found)
    }
  }


  override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
    // when year = 0 clear publish date
    if (year == 0) {
      updatePublishDate("")
      return
    }
    val date = DateTimeUtils.getDateStringFromDayMonthYear(year, month, day,
        getString(R.string.date_format_yyyy_MM_dd))
    date?.let { updatePublishDate(it) }
  }

  override fun onError(baseException: BaseException) {
    mDialogManager.dialogError(baseException.getMessageError())
  }

  fun onClickArrowBack() {
    mNavigator.finishActivity()
  }

  fun onClickCreateBook() {
    if (!mPresenter.validateDataInput(mBookRequest.get())) {
      return
    }
    mPresenter.addBook(mBookRequest.get())
  }

  fun onClickAddImage() {
    EasyImage.openChooserWithGallery(this, getString(R.string.select_image_from_gallery_or_camera),
        0)
  }

  fun onPickOffice() {
    val workSpace: MutableList<String?> = mutableListOf()
    mListOffice.mapTo(workSpace) { it.name }
    mDialogManager.dialogListSingleChoice(getString(R.string.select_office), workSpace,
        mCurrentOfficePosition,
        MaterialDialog.ListCallbackSingleChoice { _, _, position, _ ->
          mCurrentOfficePosition = position
          updateCurrentOffice(mCurrentOfficePosition)
          true
        })
  }

  fun onPickCategory() {
    val listCategory: MutableList<String?> = mutableListOf()
    mListCategory.mapTo(listCategory) { it.name }
    mDialogManager.dialogListSingleChoice(getString(R.string.select_category), listCategory,
        mCurrentCategoryPosition,
        MaterialDialog.ListCallbackSingleChoice { _, _, position, _ ->
          mCurrentCategoryPosition = position
          updateCurrentCategory(mCurrentCategoryPosition)
          true
        })
  }

  fun onPickPublishDate() {
    mDialogManager.showDatePickerDialog()
  }

  fun onClickImportFromInternalBook() {
    if (StringUtils.isBlank(mBookRequest.get().title)) {
      showSnackBar(R.string.please_enter_title)
      return
    }
    mPresenter.searchBookFromInternal(mBookRequest.get().title, getString(R.string.title))
  }

  fun onClickImportFromGoogleBook() {
    if (StringUtils.isBlank(mBookRequest.get().title)) {
      showSnackBar(R.string.please_enter_title)
      return
    }
    mPresenter.searchBookFromGoogleBook(mBookRequest.get().title)
  }

  private fun updateCurrentOffice(position: Int) {
    if (mListOffice.isEmpty()) {
      return
    }
    mCurrentOffice.set(mListOffice[position].name)
    mBookRequest.get().officeId = mListOffice[position].id
  }

  private fun updateCurrentCategory(position: Int) {
    if (mListCategory.isEmpty()) {
      return
    }
    mCurrentCategory.set(mListCategory[position].name)
    mBookRequest.get().categoryId = mListCategory[position].id
  }

  private fun updatePublishDate(date: String) {
    mPublishDate.set(date)
    mBookRequest.get().publishDate = date
  }

  private fun updateDataImage(images: List<File>) {
    val listImage = mutableListOf<BookRequest.Image>()
    for (image in images) {
      val imageSelected: BookRequest.Image = BookRequest.Image()
      imageSelected.image = image
      listImage.add(imageSelected)
    }
    mImageSelectedAdapter.updateData(listImage)
  }

  private fun updateFromInternalBook(book: Book?) {
    book?.let {
      mBookRequest.get().title = book.title
      mBookRequest.get().description = book.description
      mBookRequest.get().author = book.author
      book.publishDate?.let { updatePublishDate(it) }
      mCurrentOffice.set(book.office?.name)
      mCurrentCategory.set(book.category?.name)
      mCurrentOfficePosition = searchCurrentPositionOffice(book.office?.name, mListOffice)
      mCurrentCategoryPosition = searchCurrentPositionCategory(book.category?.name, mListCategory)
      mBookRequest.notifyChange()
    }
  }

  private fun updateFromGoogleBook(book: GoogleBook?) {
    book?.let {
      mBookRequest.get().title = book.volumeInfo?.title
      mBookRequest.get().description = book.volumeInfo?.description
      book.volumeInfo?.publishedDate?.let { updatePublishDate(it) }
      mBookRequest.get().author = book.volumeInfo?.listAuthor.toString()
      mBookRequest.notifyChange()
    }
  }

  private fun showSnackBar(title: Int) {
    mDialogManager.showSnackBarNoActionBar(mBinding.appbarLayout, title)
  }

  private fun searchCurrentPositionOffice(office: String?, listOffice: List<Office>?): Int {
    listOffice?.let {
      (0 until listOffice.size)
          .filter { listOffice[it].name == office }
          .forEach { return it }
    }
    return 0
  }

  private fun searchCurrentPositionCategory(category: String?, listCategory: List<Category>?): Int {
    listCategory?.let {
      (0 until listCategory.size)
          .filter { listCategory[it].name == category }
          .forEach { return it }
    }
    return 0
  }
}
