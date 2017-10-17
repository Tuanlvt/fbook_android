package com.framgia.fbook.screen.approverequest

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.databinding.ItemApproveRequestBinding
import com.framgia.fbook.screen.BaseRecyclerViewAdapter

/**
 * Created by framgia on 06/09/2017.
 */
class ApproveRequestAdapter constructor(
    context: Context) : BaseRecyclerViewAdapter<ApproveRequestAdapter.ItemViewHolder>(context) {

  private val mBooks: MutableList<Book> = ArrayList<Book>()
  private lateinit var mItemApproveRequestClickListener: ItemApproveRequestClickListener

  fun updateData(listBook: List<Book>) {
    mBooks.clear()
    val listSort = listBook.sortedWith(compareByDescending({ it.usersWaitings?.size }))
    mBooks.addAll(listSort)
    notifyDataSetChanged()
  }

  fun setItemApproveRequestListener(
      itemApproveRequestClickListener: ItemApproveRequestClickListener) {
    mItemApproveRequestClickListener = itemApproveRequestClickListener
  }

  fun updateNumberOfWaitingIncrease(position: Int) {
    val currentNumberOfWaiting = mBooks[position].numberOfWaiting
    mBooks[position].numberOfWaiting = currentNumberOfWaiting?.plus(1)
    notifyDataSetChanged()
  }

  fun updateNumberOfWaitingDecrease(position: Int) {
    val currentNumberOfWaiting = mBooks[position].numberOfWaiting
    mBooks[position].numberOfWaiting = currentNumberOfWaiting?.minus(1)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val binding = DataBindingUtil.inflate<ItemApproveRequestBinding>(
        LayoutInflater.from(parent.context),
        R.layout.item_approve_request, parent, false)
    return ItemViewHolder(context, binding, mItemApproveRequestClickListener)
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(mBooks[position], position)
  }

  override fun getItemCount(): Int {
    return mBooks.size
  }

  class ItemViewHolder(private val context: Context,
      private val mBinding: ItemApproveRequestBinding,
      private val mItemClickListener: ItemApproveRequestClickListener?) : RecyclerView.ViewHolder(
      mBinding.root) {

    fun bind(book: Book, position: Int) {
      mBinding.viewModel = ItemApproveRequestViewModel(context, book, mItemClickListener, position)
      mBinding.executePendingBindings()
    }
  }
}
