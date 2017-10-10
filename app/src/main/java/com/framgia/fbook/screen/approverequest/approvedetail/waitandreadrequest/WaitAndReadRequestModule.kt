package com.framgia.fbook.screen.approverequest.approvedetail.waitandreadrequest

import android.support.v4.app.Fragment
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.screen.approverequest.approvedetail.adapterlistrequest.ListRequestAdapter
import com.framgia.fbook.utils.dagger.FragmentScope
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
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
  fun providePresenter(userRepository: UserRepository,
      schedulerProvider: BaseSchedulerProvider): WaitAndReadRequestContract.Presenter {
    val presenter = WaitAndReadRequestPresenter(userRepository)
    presenter.setViewModel(mFragment as WaitAndReadRequestContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }

  @FragmentScope
  @Provides
  fun provideListRequestAdapter(): ListRequestAdapter {
    return ListRequestAdapter(mFragment.context)
  }

  @FragmentScope
  @Provides
  fun provideUserRepository(userLocalDataSource: UserLocalDataSource,
      userRemoteDataSource: UserRemoteDataSource): UserRepository {
    return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
  }

  @FragmentScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mFragment.context)
  }

}
