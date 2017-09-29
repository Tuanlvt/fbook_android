package com.framgia.fbook.screen.approverequest.approvedetail

import android.app.Activity
import android.support.v4.app.FragmentActivity
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.utils.dagger.ActivityScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
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
  fun providePresenter(userRepository: UserRepository,
      schedulerProvider: BaseSchedulerProvider): ApproveDetailContract.Presenter {
    val presenter = ApproveDetailPresenter(userRepository)
    presenter.setViewModel(mActivity as ApproveDetailContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }

  @ActivityScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mActivity)
  }

  @ActivityScope
  @Provides
  fun provideApproveDetailAdapter(): ApproveDetailAdapter {
    return ApproveDetailAdapter(mActivity, (mActivity as FragmentActivity).supportFragmentManager)
  }

  @ActivityScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mActivity)
  }

  @ActivityScope
  @Provides
  fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource,
      userLocalDataSource: UserLocalDataSource): UserRepository {
    return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
  }

  @ActivityScope
  @Provides
  fun provideApproveDetailOwnerAdapter(): ApproveDetailOwnerAdapter {
    return ApproveDetailOwnerAdapter(mActivity)
  }
}
