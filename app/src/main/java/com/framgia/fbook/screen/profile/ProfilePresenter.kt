package com.framgia.fbook.screen.profile;

import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Listens to user actions from the UI ({@link ProfileActivity}), retrieves the data and updates
 * the UI as required.
 */
class ProfilePresenter(private val mUserRepository: UserRepository) : ProfileContract.Presenter {

  private var mViewModel: ProfileContract.ViewModel? = null
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
  private lateinit var mBaseSchedulerProvider: BaseSchedulerProvider

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun getUserOtherProfile(idUser: Int?) {
    val disposable: Disposable = mUserRepository.getOtherUserProfile(idUser)
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
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
        .subscribe({
          follow ->
          mViewModel?.onGetFollowInfomationOfUserSuccess(follow.items)
        },
            { error -> mViewModel?.onError(error as BaseException) })
    mCompositeDisposable.addAll(disposable)
  }

  override fun setViewModel(viewModel: ProfileContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setSchedulerProvider(baseSchedulerProvider: BaseSchedulerProvider) {
    mBaseSchedulerProvider = baseSchedulerProvider
  }
}
