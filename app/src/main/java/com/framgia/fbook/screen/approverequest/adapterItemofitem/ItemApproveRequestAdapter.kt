package com.framgia.fbook.screen.approverequest.adapterItemofitem

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.databinding.ItemOfItemApproveRequestBinding
import com.framgia.fbook.screen.BaseRecyclerViewAdapter

/**
 * Created by Hyperion on 26/09/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ItemApproveRequestAdapter(
    context: Context, private val mBookId: Int?,
    private val mListUser: List<User>?) : BaseRecyclerViewAdapter<ItemApproveRequestAdapter.ItemViewHolder>(
    context) {
  private lateinit var mItemOfItemApproveClickListener: ItemOfItemApproveClickListener

  fun setItemOfItemApproveClickListener(itemApproveClickListener: ItemOfItemApproveClickListener) {
    mItemOfItemApproveClickListener = itemApproveClickListener
  }

  override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
    mListUser?.let {
      holder?.bindData(mBookId, mListUser[position])
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val binding = DataBindingUtil.inflate<ItemOfItemApproveRequestBinding>(
        LayoutInflater.from(parent.context),
        R.layout.item_of_item_approve_request, parent, false)
    return ItemViewHolder(binding, mItemOfItemApproveClickListener)
  }

  override fun getItemCount(): Int {
    if (mListUser == null) {
      return 0
    }
    return mListUser.size
  }

  inner class ItemViewHolder(
      private val mBinding: ItemOfItemApproveRequestBinding,
      private val mItemOfItemApproveClickListener: ItemOfItemApproveClickListener) : RecyclerView.ViewHolder(
      mBinding.root) {
    fun bindData(bookId: Int?, user: User) {
      mBinding.viewModel = ItemOfItemApproveRequestViewModel(bookId, user,
          mItemOfItemApproveClickListener)
      mBinding.executePendingBindings()
    }
  }
}
