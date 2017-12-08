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
import com.framgia.fbook.databinding.ActivityUpdateBookBinding
import com.framgia.fbook.screen.BaseActivity
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.common.DateTimeUtils
import com.fstyle.structure_android.widget.dialog.DialogManager
import java.util.*
import javax.inject.Inject

/**
 * UpdateBook Screen.
 */
class UpdateBookActivity : BaseActivity(), UpdateBookContract.ViewModel, DatePickerDialog.OnDateSetListener {

  @Inject
  internal lateinit var presenter: UpdateBookContract.Presenter
  @Inject
  internal lateinit var mDialogManager: DialogManager

  var mBook = Book()
  val titleError: ObservableField<String> = ObservableField()
  val authorError: ObservableField<String> = ObservableField()
  val descriptionError: ObservableField<String> = ObservableField()
  val mPublishDate: ObservableField<String> = ObservableField()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setUpActionBar(true, R.string.form_edit_book_to_admin)

    DaggerUpdateBookComponent.builder()
        .appComponent((application as MainApplication).appComponent)
        .updateBookModule(UpdateBookModule(this))
        .build()
        .inject(this)

    val binding = DataBindingUtil.setContentView<ActivityUpdateBookBinding>(this,
        R.layout.activity_update_book)

    binding.viewModel = this
    initData()
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
  }

  override fun onStop() {
    presenter.onStop()
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
      updateDatePublish("")
      return
    }
    val date = DateTimeUtils.getDateStringFromDayMonthYear(year, month, day,
        getString(R.string.date_format_yyyy_MM_dd))
    date?.let { updateDatePublish(it) }
  }


  private fun initData() {
    mBook = intent.extras.getParcelable(Constant.BOOK_UPDATE_EXTRA)
    mPublishDate.set(mBook.publishDate)
    mDialogManager.dialogDatePicker(this, Calendar.getInstance())
  }

  private fun updateDatePublish(date: String?) {
    mPublishDate.set(date)
  }

  fun onClickSendRequest() {
    //Todo edit later
  }

  fun onClickImportFromInternalBook() {
    //Todo edit later
  }

  fun onClickImportFromGoogleBook() {
    //Todo edit later
  }

  fun onPickDatePublish() {
    mDialogManager.showDatePickerDialog()
  }
}
