package com.framgia.fbook.screen.profile;

import com.framgia.fbook.data.model.Book
import com.framgia.fbook.data.model.BookInUser
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.FollowOrUnFollowUserRequest
import com.framgia.fbook.data.source.remote.api.response.BaseBookRespone
import com.framgia.fbook.data.source.remote.api.response.BaseResponse
import com.framgia.fbook.utils.Constant
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function5

/**
 * Listens to user actions from the UI ({@link ProfileActivity}), retrieves the data and updates
 * the UI as required.
 */
open class ProfilePresenter(
    private val mUserRepository: UserRepository,
    private val mBookRepository: BookRepository) : ProfileContract.Presenter {

  private var mViewModel: ProfileContract.ViewModel? = null
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
  private lateinit var mBaseSchedulerProvider: BaseSchedulerProvider

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun getSizeBookInUserProfile(userId: Int?) {
    val disable: Disposable = Single.zip(mBookRepository.getFeatureOtherOfUser(userId,
        Constant.RequestCodeBookInUser.TAB_READING_BOOK),
        mBookRepository.getFeatureOtherOfUser(userId,
            Constant.RequestCodeBookInUser.TAB_WAITING_BOOK),
        mBookRepository.getFeatureOtherOfUser(userId,
            Constant.RequestCodeBookInUser.TAB_SHARING_BOOK),
        mBookRepository.getFeatureOtherOfUser(userId,
            Constant.RequestCodeBookInUser.TAB_RETURNED_BOOK),
        mBookRepository.getFeatureOtherOfUser(userId,
            Constant.RequestCodeBookInUser.TAB_REVIEWED_BOOK),
        Function5<BaseResponse<BaseBookRespone<List<Book>>>, BaseResponse<BaseBookRespone<List<Book>>>, BaseResponse<BaseBookRespone<List<Book>>>, BaseResponse<BaseBookRespone<List<Book>>>, BaseResponse<BaseBookRespone<List<Book>>>, BookInUser>
        { sizeReadBook, sizeWaitingBook, sizeSharingBook, sizeSuggestedBook, sizeReviewBook ->
          BookInUser(sizeReadBook.items?.data?.size, sizeWaitingBook.items?.data?.size,
              sizeSharingBook.items?.data?.size, sizeSuggestedBook.items?.data?.size,
              sizeReviewBook.items?.data?.size)
        }).subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .doOnSubscribe({ mViewModel?.onShowProgressDialog() })
        .doAfterTerminate({ mViewModel?.onDismissProgressDialog() })
        .subscribe(
            { listSizeBookInUser ->
              mViewModel?.onGetSizeBookInUserProfileSuccess(listSizeBookInUser)
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disable)
  }

  override fun getUserOtherProfile(idUser: Int?) {
    val disposable: Disposable = mUserRepository.getOtherUserProfile(idUser)
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .doOnSubscribe({ mViewModel?.onShowProgressDialog() })
        .doAfterTerminate({ mViewModel?.onDismissProgressDialog() })
        .subscribe(
            { user ->
              mViewModel?.onGetUserOtherProfileSuccess(user.item)
            },
            { error ->
              mViewModel?.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  override fun getFollowInfomationOfUser(idUser: Int?) {
    val disposable: Disposable = mUserRepository.getFollowInfomationOfUser(idUser)
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .doOnSubscribe({ mViewModel?.onShowProgressDialog() })
        .doAfterTerminate({ mViewModel?.onDismissProgressDialog() })
        .subscribe({ follow ->
          mViewModel?.onGetFollowInfomationOfUserSuccess(follow.items)
        },
            { error -> mViewModel?.onError(error as BaseException) })
    mCompositeDisposable.addAll(disposable)
  }

  override fun followOrUnFollow(followOrUnFollowUserRequest: FollowOrUnFollowUserRequest?) {
    val disposable: Disposable = mUserRepository.followOrUnFollowUser(followOrUnFollowUserRequest)
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .doOnSubscribe({ mViewModel?.onShowProgressDialog() })
        .doAfterTerminate({ mViewModel?.onDismissProgressDialog() })
        .subscribe({
          mViewModel?.onFollowOrUnFollowSuccess()
        }, { error ->
          mViewModel?.onError(error as BaseException)
        })
    mCompositeDisposable.add(disposable)
  }

  override fun setViewModel(viewModel: ProfileContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(baseSchedulerProvider: BaseSchedulerProvider) {
    mBaseSchedulerProvider = baseSchedulerProvider
  }
}
