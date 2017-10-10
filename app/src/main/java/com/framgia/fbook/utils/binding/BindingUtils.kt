package com.framgia.fbook.utils.binding

import android.databinding.BindingAdapter
import android.net.Uri
import android.support.design.widget.TabLayout
import android.support.design.widget.TextInputLayout
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.framgia.fbook.R
import com.framgia.fbook.utils.common.StringUtils


/**
 * Created by le.quang.dao on 20/03/2017.
 */

object BindingUtils {

  /**
   * setErrorMessage for TextInputLayout
   */
  @JvmStatic
  @BindingAdapter("errorText")
  fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
    val editText = view.editText
    editText?.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(p0: Editable?) {
        view.error = ""
      }

      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
      }

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
      }

    })
  }

  /**
   * Load image from url
   */
  @JvmStatic
  @BindingAdapter("imageUrl")
  fun loadImage(imageView: ImageView, url: String?) {
    var url = url
    if (StringUtils.isBlank(url)) {
      url = ""
    }
    val uri = Uri.parse(url)
    Glide.with(imageView.context)
        .load(uri)
        .placeholder(R.drawable.ic_book_place_holder)
        .dontAnimate()
        .into(imageView)
  }

  /**
   * Load image user from url
   */
  @JvmStatic
  @BindingAdapter("imageUserUrl")
  fun loadImageUser(imageView: ImageView, url: String?) {
    var url = url
    if (StringUtils.isBlank(url)) {
      url = ""
    }
    val uri = Uri.parse(url)
    Glide.with(imageView.context)
        .load(uri)
        .placeholder(R.drawable.ic_user_place_holder)
        .dontAnimate()
        .into(imageView)
  }

  /**
   * setAdapter For RecyclerView
   */
  @JvmStatic
  @BindingAdapter("recyclerAdapter")
  fun setAdapterForRecyclerView(recyclerView: RecyclerView,
      adapter: RecyclerView.Adapter<*>) {
    recyclerView.adapter = adapter
  }

  /**
   * addItemDecoration For RecyclerView
   */
  @JvmStatic
  @BindingAdapter("addItemDecoration")
  fun setItemDecoration(recyclerView: RecyclerView, orientation: Int) {
    recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, orientation))
  }

  /**
   * setAdapter for viewpager
   */
  @JvmStatic
  @BindingAdapter("viewPageAdapter")
  fun setViewPageAdapter(viewPager: ViewPager, adapter: FragmentPagerAdapter) {
    viewPager.adapter = adapter
  }

  @JvmStatic
  @BindingAdapter("setViewPager")
  fun setUpViewPager(tabLayout: TabLayout, viewPager: ViewPager) {
    tabLayout.setupWithViewPager(viewPager)
  }

  @JvmStatic
  @BindingAdapter("pager")
  fun setViewPagerTabs(tabLayout: TabLayout, viewPager: ViewPager) {
    tabLayout.setupWithViewPager(viewPager, true)
  }

  @JvmStatic
  @BindingAdapter("currentFragment")
  fun setCurrentViewPager(viewPager: ViewPager, currentPage: Int) {
    viewPager.currentItem = currentPage
  }

  @JvmStatic
  @BindingAdapter("parseHtlmToText")
  fun setFromHtlmToText(text: TextView, description: String?) {
    if (StringUtils.isBlank(description)) {
      return
    }
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
      text.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY))
      return
    }
    text.setText(Html.fromHtml(description))
  }

  @JvmStatic
  @BindingAdapter("setSize")
  fun setSize(textView: TextView, size: Int?) {
    textView.text = size.toString()
    textView.setBackgroundResource(R.drawable.background_hexagon)
  }
}
