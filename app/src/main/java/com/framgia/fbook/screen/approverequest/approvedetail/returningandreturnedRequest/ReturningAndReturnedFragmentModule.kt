package com.framgia.fbook.screen.approverequest.approvedetail.returningandreturnedRequest

import android.support.v4.app.Fragment
import com.framgia.fbook.utils.dagger.FragmentScope
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the [ReturningAndReturnedFragmentPresenter].
 */
@Module
class ReturningAndReturnedFragmentModule(private val mFragment: Fragment) {

  @FragmentScope
  @Provides
  fun providePresenter(
      schedulerProvider: BaseSchedulerProvider): ReturningAndReturnedFragmentContract.Presenter {
    val presenter = ReturningAndReturnedFragmentPresenter()
    presenter.setViewModel(mFragment as ReturningAndReturnedFragmentContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }
}
