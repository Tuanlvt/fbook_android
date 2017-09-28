package com.framgia.fbook.screen.otheruser;

import android.app.Activity
import android.support.v4.app.FragmentActivity
import com.framgia.fbook.utils.dagger.ActivityScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link OtherUserPresenter}.
 */
@Module
class OtherUserModule(private val mActivity: Activity) {

  @ActivityScope
  @Provides
  fun providePresenter(
      basesShedulerProvider: BaseSchedulerProvider): OtherUserContract.Presenter {
    val presenter = OtherUserPresenter()
    presenter.setSchedulerProvider(basesShedulerProvider)
    presenter.setViewModel(mActivity as OtherUserContract.ViewModel)
    return presenter
  }

  @ActivityScope
  @Provides
  fun provideOtherUserAdapter(): OtherUserAdapter {
    return OtherUserAdapter(mActivity,
        (mActivity as FragmentActivity).supportFragmentManager)
  }

  @ActivityScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mActivity)
  }
}
