package com.framgia.fbook.screen.notification

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.ItemNotification
import com.framgia.fbook.databinding.ItemNotificationBinding
import com.framgia.fbook.screen.BaseRecyclerViewAdapter

/**
 * Created by Hyperion on 05/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class NotificationAdapter(
    context: Context) : BaseRecyclerViewAdapter<NotificationAdapter.ItemViewHolder>(context) {

  private val mListNotification = mutableListOf<ItemNotification>()
  private lateinit var mItemNotificationClickListener: ItemNotificationClickListener
  private var mType = 0

  companion object {
    private val VIEWED = 1
  }

  fun updateData(listNotification: List<ItemNotification>?, type: Int) {
    mListNotification.addAll(listNotification!!)
    mType = type
    notifyDataSetChanged()
  }

  fun updateItem(position: Int) {
    mListNotification[position].viewed = VIEWED
    notifyItemChanged(position, mListNotification)
  }

  fun setItemNotificationClickListener(
      itemNotificationClickListener: ItemNotificationClickListener) {
    mItemNotificationClickListener = itemNotificationClickListener
  }

  override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
    holder?.bindData(mListNotification[position], mType, position)
  }

  override fun getItemCount(): Int = mListNotification.size

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
    val binding = DataBindingUtil.inflate<ItemNotificationBinding>(
        LayoutInflater.from(parent?.context), R.layout.item_notification, parent, false)
    return ItemViewHolder(binding)
  }

  inner class ItemViewHolder(
      private val mBinding: ItemNotificationBinding) : RecyclerView.ViewHolder(mBinding.root) {

    fun bindData(itemNotification: ItemNotification, type: Int, position: Int) {
      mBinding.viewModel = ItemNotificationViewModel(context, itemNotification,
          mItemNotificationClickListener, type, position)
      mBinding.executePendingBindings()
    }
  }
}
