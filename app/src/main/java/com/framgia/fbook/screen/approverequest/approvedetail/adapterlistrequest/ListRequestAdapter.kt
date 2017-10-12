package com.framgia.fbook.screen.approverequest.approvedetail.adapterlistrequest

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.User
import com.framgia.fbook.databinding.ItemListRequestBinding
import com.framgia.fbook.screen.BaseRecyclerViewAdapter

/**
 * Created by Hyperion on 29/09/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
class ListRequestAdapter(
    context: Context) : BaseRecyclerViewAdapter<ListRequestAdapter.ItemViewHolder>(
    context) {
  private lateinit var mItemRequestClickListener: ItemRequestClickListener
  private val mListUser = mutableListOf<User>()
  private var mIsApproved = false
  private var mIsReturned = false
  private var sizeApprove: Int = 0

  fun updateData(listUser: List<User>?) {
    listUser?.let {
      if (mIsApproved) {
        sizeApprove = listUser.size - 1
      }
      mListUser.addAll(listUser)
      mListUser.reverse()
      notifyDataSetChanged()
    }
  }

  fun setIsApproved(approved: Boolean) {
    mIsApproved = approved
  }

  fun setIsReturned(returned: Boolean) {
    mIsReturned = returned
  }

  fun setItemRequestClickListener(
      itemRequestClickListener: ItemRequestClickListener) {
    mItemRequestClickListener = itemRequestClickListener
  }

  override fun getItemCount(): Int = mListUser.size

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
    val binding = DataBindingUtil.inflate<ItemListRequestBinding>(
        LayoutInflater.from(parent?.context),
        R.layout.item_list_request, parent, false)
    return ItemViewHolder(binding, mItemRequestClickListener)
  }

  override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
    if (mIsApproved && (sizeApprove >= position)) {
      holder?.bindData(mListUser[position], true)
    } else {
      holder?.bindData(mListUser[position], false)
    }
  }

  inner class ItemViewHolder(
      private val mBinding: ItemListRequestBinding,
      private val mItemRequestClickListener: ItemRequestClickListener) : RecyclerView.ViewHolder
  (mBinding.root) {

    fun bindData(user: User, approve: Boolean) {
      mBinding.viewModel = ItemListRequestViewModel(context, user, approve, mIsApproved,
          mIsReturned, mItemRequestClickListener)
      mBinding.executePendingBindings()
    }
  }
}
