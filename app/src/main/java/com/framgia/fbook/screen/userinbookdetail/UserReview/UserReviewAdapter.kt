package com.framgia.fbook.screen.userinbookdetail.UserReview

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.framgia.fbook.R
import com.framgia.fbook.data.model.Book
import com.framgia.fbook.databinding.ItemListUserReviewBinding
import com.framgia.fbook.screen.BaseRecyclerViewAdapter

/**
 * Created by framgia on 22/09/2017.
 */
class UserReviewAdapter constructor(
    context: Context) : BaseRecyclerViewAdapter<UserReviewAdapter.ItemViewHolder>(context) {

  private val mReviewDetails = arrayListOf<Book.ReviewDetail>()
  private lateinit var mItemUserReviewClickListener: ItemUserReviewClickListener
  fun updateData(reviewDetails: List<Book.ReviewDetail>?) {
    reviewDetails?.let {
      mReviewDetails.clear()
      mReviewDetails.addAll(it)
    }
    notifyDataSetChanged()
  }

  fun setItemUserClickListener(itemUserReviewClickListener: ItemUserReviewClickListener) {
    mItemUserReviewClickListener = itemUserReviewClickListener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val binding = DataBindingUtil.inflate<ItemListUserReviewBinding>(
        LayoutInflater.from(parent.context),
        R.layout.item_list_user_review, parent, false)
    return ItemViewHolder(binding, mItemUserReviewClickListener)
  }

  override fun onBindViewHolder(holder: UserReviewAdapter.ItemViewHolder, position: Int) {
    holder.bind(mReviewDetails[position])
  }

  override fun getItemCount(): Int {
    return mReviewDetails.size
  }

  class ItemViewHolder(private val mBinding: ItemListUserReviewBinding,
      private val mItemUserReviewClickListener: ItemUserReviewClickListener?) : RecyclerView.ViewHolder(
      mBinding.root) {

    fun bind(reviewDetail: Book.ReviewDetail) {
      mBinding.viewModel = ItemUserReviewViewModel(reviewDetail, mItemUserReviewClickListener)
      mBinding.executePendingBindings()
    }
  }
}
