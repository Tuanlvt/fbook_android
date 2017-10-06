package com.framgia.fbook.screen.otheruser.bookInUser

import android.support.v4.app.Fragment
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.BookRepositoryImpl
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.BookRemoteDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.utils.dagger.FragmentScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the [BookInUserPresenter].
 */
@Module
class BookInUserModule(private val mFragment: Fragment) {

  @FragmentScope
  @Provides
  fun providePresenter(bookRepository: BookRepository,
      baseSchedulerProvider: BaseSchedulerProvider): BookInUserContract.Presenter {
    val presenter = BookInUserPresenter(bookRepository)
    presenter.setSchedulerProvider(baseSchedulerProvider)
    presenter.setViewModel(mFragment as BookInUserContract.ViewModel)
    return presenter
  }

  @FragmentScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mFragment)
  }

  @FragmentScope
  @Provides
  fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource,
      userLocalDataSource: UserLocalDataSource): UserRepository {
    return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
  }

  @FragmentScope
  @Provides
  fun provideBookRepository(bookRemoteDataSource: BookRemoteDataSource): BookRepository {
    return BookRepositoryImpl(bookRemoteDataSource)
  }

  @FragmentScope
  @Provides
  fun provideBookInUserAdapter(): BookInUserAdapter {
    return BookInUserAdapter(mFragment.context.applicationContext)
  }
}
