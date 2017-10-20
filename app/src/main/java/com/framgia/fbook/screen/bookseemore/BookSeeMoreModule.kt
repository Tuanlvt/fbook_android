package com.framgia.fbook.screen.bookseemore;

import android.app.Activity
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.BookRepositoryImpl
import com.framgia.fbook.data.source.CategoryRepository
import com.framgia.fbook.data.source.CategoryRepositoryImpl
import com.framgia.fbook.data.source.remote.BookRemoteDataSource
import com.framgia.fbook.data.source.remote.CategoryRemoteDataSource
import com.framgia.fbook.screen.bookseemore.adapter.BookSeeMoreAdapter
import com.framgia.fbook.utils.dagger.ActivityScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link BookSeeMorePresenter}.
 */
@Module
class BookSeeMoreModule(private val mActivity: Activity) {

  @ActivityScope
  @Provides
  fun providePresenter(categoryRepository: CategoryRepository, bookRepository: BookRepository,
      schedulerProvider: BaseSchedulerProvider): BookSeeMoreContract.Presenter {
    val presenter = BookSeeMorePresenter(categoryRepository, bookRepository)
    presenter.setViewModel(mActivity as BookSeeMoreContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }

  @ActivityScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mActivity)
  }

  @ActivityScope
  @Provides
  fun provideBookRepository(bookRemoteDataSource: BookRemoteDataSource): BookRepository {
    return BookRepositoryImpl(bookRemoteDataSource)
  }

  @ActivityScope
  @Provides
  fun provideListBookAdapter(): BookSeeMoreAdapter {
    return BookSeeMoreAdapter(mActivity)
  }

  @ActivityScope
  @Provides
  fun provideCategoryRepository(
      categoryRemoteDataSource: CategoryRemoteDataSource): CategoryRepository {
    return CategoryRepositoryImpl(categoryRemoteDataSource)
  }

  @ActivityScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mActivity)
  }
}
