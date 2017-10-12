package com.framgia.fbook.screen.main

import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.framia.fbook.screen.main.MainContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by daolq on 8/14/17.
 */
class MainPresenter(private val mUserRepository: UserRepository) : MainContract.Presenter {

  private lateinit var mMainViewModel: MainContract.ViewModel
  private lateinit var mBaseSchedulerProvider: BaseSchedulerProvider
  private val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

  override fun onStart() {
  }

  override fun onStop() {
    mCompositeDisposable.clear()
  }

  override fun setViewModel(viewModel: MainContract.ViewModel) {
    mMainViewModel = viewModel
  }

  fun setSchedulerProvider(baseSchedulerProvider: BaseSchedulerProvider) {
    mBaseSchedulerProvider = baseSchedulerProvider
  }

  override fun getOffices() {
    val disposable: Disposable = mUserRepository.getOffices()
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .subscribe(
            { offices ->
              mMainViewModel.onGetOfficeSuccess(offices.items)
            },
            { error ->
              mMainViewModel.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }

  override fun getCountNotification() {
    val disposable = mUserRepository.getCountNotification()
        .subscribeOn(mBaseSchedulerProvider.io())
        .observeOn(mBaseSchedulerProvider.ui())
        .subscribe(
            { countResponse ->
              mMainViewModel.onGetCountNotificationSuccess(countResponse.item?.count)
            },
            { error ->
              mMainViewModel.onError(error as BaseException)
            })
    mCompositeDisposable.add(disposable)
  }
}
