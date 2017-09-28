package com.framgia.fbook.screen.approverequest.approvedetail;

import android.app.Activity
import com.framgia.fbook.utils.dagger.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ApproveDetailPresenter}.
 */
@Module
class ApproveDetailModule(private val mActivity: Activity) {

  @ActivityScope
  @Provides
  fun providePresenter(): ApproveDetailContract.Presenter {
    val presenter = ApproveDetailPresenter()
    presenter.setViewModel(mActivity as ApproveDetailContract.ViewModel)
    return presenter
  }
}
