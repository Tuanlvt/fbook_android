package com.fstyle.structure_android.widget.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.support.annotation.ArrayRes
import android.support.annotation.DrawableRes
import android.widget.DatePicker
import com.framgia.fbook.R
import com.fstyle.library.MaterialDialog
import java.util.*

/**
 * Created by le.quang.dao on 14/03/2017.
 */

open class DialogManagerImpl(private val mContext: Context) : DialogManager {
  private var mProgressDialog: MaterialDialog? = null
  private var mDatePickerDialog: DatePickerDialog? = null

  override fun showIndeterminateProgressDialog() {
    if (mProgressDialog == null) {
      mProgressDialog = MaterialDialog.Builder(mContext).content("Please wait…")
          .progress(true, 0)
          .canceledOnTouchOutside(false)
          .build()
    }
    mProgressDialog?.show()
  }

  override fun dismissProgressDialog() {
    if (mProgressDialog == null) {
      return
    }
    mProgressDialog?.dismiss()
  }

  override fun dialogError(content: String) {
    MaterialDialog.Builder(mContext).content(content)
        .positiveText(android.R.string.ok)
        .show()
  }

  override fun dialogBasicWithoutTitle(content: String,
      positiveButtonListener: MaterialDialog.SingleButtonCallback) {
    MaterialDialog.Builder(mContext).content(content)
        .positiveText(R.string.agree)
        .negativeText(R.string.cancel)
        .onPositive(positiveButtonListener)
        .show()
  }

  override fun dialogBasic(title: String, content: String,
      positiveButtonListener: MaterialDialog.SingleButtonCallback) {
    MaterialDialog.Builder(mContext).title(title)
        .content(content)
        .positiveText(R.string.agree)
        .negativeText(R.string.cancel)
        .cancelable(false)
        .onPositive(positiveButtonListener)
        .show()
  }

  override fun dialogBasicIcon(title: String, content: String, @DrawableRes icon: Int,
      positiveButtonListener: MaterialDialog.SingleButtonCallback) {
    MaterialDialog.Builder(mContext).title(title)
        .content(content)
        .iconRes(icon)
        .limitIconToDefaultSize()
        .positiveText(R.string.agree)
        .negativeText(R.string.cancel)
        .onPositive(positiveButtonListener)
        .show()
  }

  override fun dialogBasicCheckPrompt(title: String,
      callback: MaterialDialog.SingleButtonCallback) {
    MaterialDialog.Builder(mContext).iconRes(R.mipmap.ic_launcher)
        .limitIconToDefaultSize()
        .title(title)
        .checkBoxPrompt(mContext.getString(R.string.dont_ask_again), false, null)
        .positiveText(R.string.allow)
        .negativeText(R.string.deny)
        .onAny(callback)
        .show()
  }

  override fun dialogNeutral(title: String, content: String,
      callback: MaterialDialog.SingleButtonCallback) {
    MaterialDialog.Builder(mContext).title(title)
        .content(content)
        .positiveText(R.string.agree)
        .negativeText(R.string.cancel)
        .neutralText(R.string.more_info)
        .onAny(callback)
        .show()
  }

  override fun dialogList(title: String, @ArrayRes arrayId: Int,
      callback: MaterialDialog.ListCallback) {
    MaterialDialog.Builder(mContext).title(title)
        .items(arrayId)
        .itemsCallback(callback)
        .show()
  }

  override fun dialogListWithoutTitle(@ArrayRes arrayId: Int,
      callback: MaterialDialog.ListCallback) {
    MaterialDialog.Builder(mContext).items(arrayId).itemsCallback(callback).show()
  }

  override fun dialogListSingleChoice(title: String, @ArrayRes arrayId: Int, selectedIndex: Int,
      callback: MaterialDialog.ListCallbackSingleChoice) {
    MaterialDialog.Builder(mContext).title(title)
        .items(arrayId)
        .itemsCallbackSingleChoice(selectedIndex, callback)
        .positiveText(R.string.choose)
        .show()
  }

  override fun dialogListSingleChoice(title: String, strings: MutableList<String?>,
      selectedIndex: Int,
      callback: MaterialDialog.ListCallbackSingleChoice) {
    MaterialDialog.Builder(mContext).title(title)
        .items(strings)
        .itemsCallbackSingleChoice(selectedIndex, callback)
        .positiveText(R.string.choose)
        .show()
  }

  override fun dialogListMultiChoice(title: String, @ArrayRes arrayId: Int,
      selectedIndices: Array<Int>, callback: MaterialDialog.ListCallbackMultiChoice) {
    MaterialDialog.Builder(mContext).title(title)
        .items(arrayId)
        .positiveText(R.string.choose)
        .autoDismiss(false)
        .neutralText(R.string.clear)
        .itemsCallbackMultiChoice(selectedIndices, callback)
        .onNeutral { materialDialog, _ -> materialDialog.clearSelectedIndices() }
        .onPositive { materialDialog, _ -> materialDialog.dismiss() }
        .show()
  }

  override fun dialogDatePicker(
      onDateSetListener: DatePickerDialog.OnDateSetListener, calendar: Calendar): DialogManager {
    mDatePickerDialog = DatePickerDialog(mContext, onDateSetListener, calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    mDatePickerDialog?.let {
      mDatePickerDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL,
          mContext.getText(R.string.clear), { _, _ ->
        val datePicker = DatePicker(mContext)
        onDateSetListener.onDateSet(datePicker, 0, 0, 0)
      })
    }
    return this
  }

  override fun showDatePickerDialog() {
    mDatePickerDialog?.let {
      mDatePickerDialog!!.show()
    }
  }
}
