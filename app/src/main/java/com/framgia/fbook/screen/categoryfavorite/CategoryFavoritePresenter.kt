package com.framgia.fbook.screen.categoryfavorite

import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Listens to user actions from the UI ([CategoryFavoriteFragment]), retrieves the data and
 * updates
 * the UI as required.
 */
internal class CategoryFavoritePresenter(
    private val userRepository: UserRepository) : CategoryFavoriteContract.Presenter {

  private var mViewModel: CategoryFavoriteContract.ViewModel? = null
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
  private lateinit var mBaseSchedulerProvider: BaseSchedulerProvider

  override fun onStart() {}

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun getUser() {
    val disposable: Disposable = userRepository.getUser()
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .subscribe({
          user ->
          mViewModel?.onGetUserSuccess(user.item)
        }, {
          error ->
          mViewModel?.onError(error as BaseException)
        })
    mCompositeDisposable.add(disposable)
  }

  override fun setViewModel(viewModel: CategoryFavoriteContract.ViewModel) {
    mViewModel = viewModel
  }

  fun setBaseSchedulerProvider(baseSchedulerProvider: BaseSchedulerProvider) {
    mBaseSchedulerProvider = baseSchedulerProvider
  }

  companion object {
    private val TAG = CategoryFavoritePresenter::class.java.name
  }
}
