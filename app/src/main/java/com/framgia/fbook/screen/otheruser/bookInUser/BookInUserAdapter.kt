package com.framgia.fbook.screen.otheruser.bookInUser

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.databinding.ItemBookInUserProfileBinding
import com.framgia.fbook.screen.BaseRecyclerViewAdapter

/**
 * Created by Hyperion on 10/6/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class BookInUserAdapter constructor(context: Context)
  : BaseRecyclerViewAdapter<BookInUserAdapter.ItemViewHolder>(context) {

  private val mBooks = arrayListOf<Book>()
  private lateinit var mItemBookInUserClickListener: ItemBookInUserClickListener
  private var mPositionTab: Int = 0
  fun updateData(reviewDetails: List<Book>?) {
    reviewDetails?.let {
      mBooks.clear()
      mBooks.addAll(it)
    }
    notifyDataSetChanged()
  }

  fun getPositionTab(positionTab: Int) {
    positionTab.let { mPositionTab = positionTab }
  }

  fun setItemBookInUserClickListener(
      itemBookInUserClickListener: ItemBookInUserClickListener) {
    mItemBookInUserClickListener = itemBookInUserClickListener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val binding = DataBindingUtil.inflate<ItemBookInUserProfileBinding>(
        LayoutInflater.from(parent.context),
        R.layout.item_book_in_user_profile, parent, false)
    return ItemViewHolder(binding, mItemBookInUserClickListener, mPositionTab)
  }

  override fun onBindViewHolder(holder: BookInUserAdapter.ItemViewHolder,
      position: Int) {
    holder.bind(mBooks[position])
  }

  override fun getItemCount(): Int {
    return mBooks.size
  }

  class ItemViewHolder(private val mBinding: ItemBookInUserProfileBinding,
      private val mItemBookInUserClickListener: ItemBookInUserClickListener?,
      val positionTab: Int?) : RecyclerView.ViewHolder(
      mBinding.root) {

    fun bind(book: Book) {
      mBinding.viewModel = ItemBookInUserViewModel(book, mItemBookInUserClickListener, positionTab)
      mBinding.executePendingBindings()
    }
  }
}
