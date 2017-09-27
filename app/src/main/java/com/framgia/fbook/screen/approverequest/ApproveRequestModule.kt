package com.framgia.fbook.screen.approverequest

import android.app.Activity
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.BookRepositoryImpl
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.BookRemoteDataSource
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
 * the {@link ApproveRequestPresenter}.
 */
@Module
class ApproveRequestModule(private val activity: Activity) {

  @ActivityScope
  @Provides
  fun providePresenter(userRepository: UserRepository, bookRepository: BookRepository,
      basesSchedulerProvider: BaseSchedulerProvider): ApproveRequestContract.Presenter {
    val presenter = ApproveRequestPresenter(userRepository, bookRepository)
    presenter.setViewModel(activity as ApproveRequestContract.ViewModel)
    presenter.setSchedulerProvider(basesSchedulerProvider)
    return presenter
  }

  @ActivityScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(activity)
  }

  @ActivityScope
  @Provides
  fun provideBookRepository(bookRemoteDataSource: BookRemoteDataSource): BookRepository {
    return BookRepositoryImpl(bookRemoteDataSource)
  }

  @ActivityScope
  @Provides
  fun provideApproveRequestAdapter(): ApproveRequestAdapter {
    return ApproveRequestAdapter(activity)
  }

  @ActivityScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(activity)
  }

  @ActivityScope
  @Provides
  fun provideUserRepository(userLocalDataSource: UserLocalDataSource,
      userRemoteDataSource: UserRemoteDataSource): UserRepository {
    return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
  }
}
