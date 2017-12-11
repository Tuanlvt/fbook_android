package com.framgia.fbook.screen

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import com.framgia.fbook.R

/**
 * Created by le.quang.dao on 10/03/2017.
 */

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
  protected fun setUpActionBar(buttonBack: Boolean, title: Int) {
    supportActionBar?.setTitle(title)
    supportActionBar?.setDisplayHomeAsUpEnabled(buttonBack)
    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
  }
}
