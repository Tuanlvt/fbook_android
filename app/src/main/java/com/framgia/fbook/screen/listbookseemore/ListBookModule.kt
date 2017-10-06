package com.framgia.fbook.screen.listbookseemore

import android.support.v4.app.Fragment
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.BookRepositoryImpl
import com.framgia.fbook.data.source.CategoryRepository
import com.framgia.fbook.data.source.CategoryRepositoryImpl
import com.framgia.fbook.data.source.remote.BookRemoteDataSource
import com.framgia.fbook.data.source.remote.CategoryRemoteDataSource
import com.framgia.fbook.screen.listbookseemore.adapter.ListBookAdapter
import com.framgia.fbook.utils.dagger.ActivityScope
import com.framgia.fbook.utils.dagger.FragmentScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the [ListBookPresenter].
 */
@Module
class ListBookModule(private val mFragment: Fragment) {

  @FragmentScope
  @Provides
  fun providePresenter(categoryRepository: CategoryRepository,bookRepository: BookRepository,
      schedulerProvider: BaseSchedulerProvider): ListBookContract.Presenter {
    val presenter = ListBookPresenter(categoryRepository,bookRepository)
    presenter.setViewModel(mFragment as ListBookContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }

  @FragmentScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mFragment.context)
  }

  @FragmentScope
  @Provides
  fun provideBookRepository(bookRemoteDataSource: BookRemoteDataSource): BookRepository {
    return BookRepositoryImpl(bookRemoteDataSource)
  }

  @FragmentScope
  @Provides
  fun provideListBookAdapter(): ListBookAdapter {
    return ListBookAdapter(mFragment.context)
  }

  @FragmentScope
  @Provides
  fun provideCategoryRepository(
      categoryRemoteDataSource: CategoryRemoteDataSource): CategoryRepository {
    return CategoryRepositoryImpl(categoryRemoteDataSource)
  }

  @FragmentScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mFragment)
  }
}
