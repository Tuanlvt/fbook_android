package com.framgia.fbook.screen.bookseemore.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.databinding.BookSeeMoreItemBinding
import com.framgia.fbook.screen.BaseRecyclerViewAdapter
import com.framgia.fbook.screen.onItemRecyclerViewClickListener

/**
 * Created by levutantuan on 10/20/17.
 */
class BookSeeMoreAdapter(
    context: Context) : BaseRecyclerViewAdapter<BookSeeMoreAdapter.ItemViewHolder>(
    context) {
  private lateinit var mItemClickListener: onItemRecyclerViewClickListener
  private val mListBook = mutableListOf<Book>()

  fun updateData(listBook: List<Book>?) {
    listBook?.let {
      mListBook.addAll(listBook)
      notifyDataSetChanged()
    }
  }

  fun clearData() {
    mListBook.clear()
  }

  fun setItemInternalBookListener(itemInternalBookListener: onItemRecyclerViewClickListener) {
    mItemClickListener = itemInternalBookListener
  }

  override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
    holder?.binData(mListBook[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
    val binding = DataBindingUtil.inflate<BookSeeMoreItemBinding>(
        LayoutInflater.from(parent?.context), R.layout.book_see_more_item, parent,
        false)
    return ItemViewHolder(binding, mItemClickListener)
  }

  override fun getItemCount(): Int = mListBook.size

  inner class ItemViewHolder(private val mBinding: BookSeeMoreItemBinding,
      private val mItemClickListener: onItemRecyclerViewClickListener?) : RecyclerView.ViewHolder(
      mBinding.root) {

    fun binData(book: Book) {
      mBinding.viewModel = ItemBookSeeMoreLViewModel(book, mItemClickListener)
      mBinding.executePendingBindings()
    }
  }
}
