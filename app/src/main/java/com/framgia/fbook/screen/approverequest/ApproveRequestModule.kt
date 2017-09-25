package com.framgia.fbook.screen.approverequest

import android.app.Activity
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.BookRepositoryImpl
import com.framgia.fbook.data.source.remote.BookRemoteDataSource
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
  fun providePresenter(bookRepository: BookRepository,
      basesSchedulerProvider: BaseSchedulerProvider): ApproveRequestContract.Presenter {
    val presenter = ApproveRequestPresenter(bookRepository)
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
}
