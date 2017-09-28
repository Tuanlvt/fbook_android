package com.framgia.fbook.screen.approverequest.approvedetail.waitandreadrequest

import android.support.v4.app.Fragment
import com.framgia.fbook.utils.dagger.FragmentScope
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the [WaitAndReadRequestPresenter].
 */
@Module
class WaitAndReadRequestModule(private val mFragment: Fragment) {

  @FragmentScope
  @Provides
  fun providePresenter(schedulerProvider: BaseSchedulerProvider): WaitAndReadRequestContract.Presenter {
    val presenter = WaitAndReadRequestPresenter()
    presenter.setViewModel(mFragment as WaitAndReadRequestContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }
}
