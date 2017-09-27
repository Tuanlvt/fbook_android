package com.framgia.fbook.screen.SearchBook.internalbook

import android.support.v4.app.Fragment
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.BookRepositoryImpl
import com.framgia.fbook.data.source.remote.BookRemoteDataSource
import com.framgia.fbook.screen.SearchBook.adaptersearchbook.SearchBookAdapter
import com.framgia.fbook.utils.dagger.FragmentScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the [InternalBookPresenter].
 */
@Module
class InternalBookModule(private val mFragment: Fragment) {

  @FragmentScope
  @Provides
  fun providePresenter(bookRepository: BookRepository,
      schedulerProvider: BaseSchedulerProvider): InternalBookContract.Presenter {
    val presenter = InternalBookPresenter(bookRepository)
    presenter.setViewModel(mFragment as InternalBookContract.ViewModel)
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
  fun provideInternalBookAdapter(): SearchBookAdapter {
    return SearchBookAdapter(
        mFragment.activity)
  }

  @FragmentScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mFragment)
  }
}
