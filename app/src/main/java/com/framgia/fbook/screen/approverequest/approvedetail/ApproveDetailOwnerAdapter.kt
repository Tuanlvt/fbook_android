package com.framgia.fbook.screen.approverequest.approvedetail

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Owner
import com.framgia.fbook.databinding.ItemApproveDetailOwnerBinding
import com.framgia.fbook.screen.BaseRecyclerViewAdapter
import com.framgia.fbook.screen.onItemRecyclerViewClickListener

/**
 * Created by Hyperion on 29/09/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ApproveDetailOwnerAdapter(
    context: Context) : BaseRecyclerViewAdapter<ApproveDetailOwnerAdapter.ItemViewHolder>(
    context) {

  private val mOwners = arrayListOf<Owner>()
  private lateinit var mOnItemRecyclerViewClickListener: onItemRecyclerViewClickListener
  fun updateData(listOwner: List<Owner>?) {
    mOwners.clear()
    listOwner?.let { mOwners.addAll(it) }
    notifyDataSetChanged()
  }

  fun setOnItemRecyclerViewClickListener(
      onItemRecyclerViewClickListener: onItemRecyclerViewClickListener) {
    mOnItemRecyclerViewClickListener = onItemRecyclerViewClickListener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder? {
    val binding = DataBindingUtil.inflate<ItemApproveDetailOwnerBinding>(
        LayoutInflater.from(parent.context),
        R.layout.item_approve_detail_owner, parent, false)
    return ItemViewHolder(binding, mOnItemRecyclerViewClickListener)
  }

  override fun onBindViewHolder(holder: ApproveDetailOwnerAdapter.ItemViewHolder, position: Int) {
    holder.bind(mOwners[position])
  }

  override fun getItemCount(): Int {
    return mOwners.size
  }

  class ItemViewHolder(private val mBinding: ItemApproveDetailOwnerBinding,
      private val mOnItemRecyclerViewClickListener: onItemRecyclerViewClickListener?) : RecyclerView.ViewHolder(
      mBinding.root) {

    fun bind(owner: Owner) {
      mBinding.viewModel = ItemApproveDetailOwnerViewModel(owner, mOnItemRecyclerViewClickListener)
      mBinding.executePendingBindings()
    }
  }
}
