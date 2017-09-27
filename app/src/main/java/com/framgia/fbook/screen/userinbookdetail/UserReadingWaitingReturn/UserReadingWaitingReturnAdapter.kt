package com.framgia.fbook.screen.userinbookdetail.UserReadingWaitingReturn

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.databinding.ItemListUserReadingWaitingReturnBinding
import com.framgia.fbook.screen.BaseRecyclerViewAdapter

/**
 * Created by framgia on 27/09/2017.
 */
class UserReadingWaitingReturnAdapter constructor(context: Context)
  : BaseRecyclerViewAdapter<UserReadingWaitingReturnAdapter.ItemViewHolder>(context) {

  private val mUsers = arrayListOf<User>()
  private lateinit var mItemUserClickListener: ItemUserClickListener
  fun updateData(reviewDetails: List<User>?) {
    reviewDetails?.let {
      mUsers.clear()
      mUsers.addAll(it)
    }
    notifyDataSetChanged()
  }

  fun setItemUserClickListener(itemUserClickListener: ItemUserClickListener) {
    mItemUserClickListener = itemUserClickListener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val binding = DataBindingUtil.inflate<ItemListUserReadingWaitingReturnBinding>(
        LayoutInflater.from(parent.context),
        R.layout.item_list_user_reading_waiting_return, parent, false)
    return ItemViewHolder(binding, mItemUserClickListener)
  }

  override fun onBindViewHolder(holder: UserReadingWaitingReturnAdapter.ItemViewHolder,
      position: Int) {
    holder.bind(mUsers[position])
  }

  override fun getItemCount(): Int {
    return mUsers.size
  }

  class ItemViewHolder(private val mBinding: ItemListUserReadingWaitingReturnBinding,
      private val mItemUserClickListener: ItemUserClickListener?) : RecyclerView.ViewHolder(
      mBinding.root) {

    fun bind(user: User) {
      mBinding.viewModel = ItemUserViewModel(user, mItemUserClickListener)
      mBinding.executePendingBindings()
    }
  }
}
