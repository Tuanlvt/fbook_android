package com.framgia.fbook.screen.mainpage

import android.support.v4.app.Fragment
import com.framgia.fbook.data.source.BookRepository
import com.framgia.fbook.data.source.BookRepositoryImpl
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.BookRemoteDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.screen.mainpage.adapter.MainPageAdapter
import com.framgia.fbook.utils.dagger.FragmentScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the [MainPagePresenter].
 */
@Module
class MainPageModule(private val mFragment: Fragment) {


  @Provides
  fun providePresenter(userRepository: UserRepository, schedulerProvider: BaseSchedulerProvider,
      bookRepository: BookRepository): MainPageContract.Presenter {
    val presenter = MainPagePresenter(userRepository, bookRepository)
    presenter.setViewModel(mFragment as MainPageContract.ViewModel)
    presenter.setSchedulerProvider(schedulerProvider)
    return presenter
  }

  @FragmentScope
  @Provides
  fun provideBookRepository(bookRemoteDataSource: BookRemoteDataSource): BookRepository {
    return BookRepositoryImpl(bookRemoteDataSource)
  }

  @FragmentScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mFragment.context)
  }

  @FragmentScope
  @Provides
  @Named("AdapterRating")
  fun provideMainPageAdapter(): MainPageAdapter {
    return MainPageAdapter(mFragment.context)
  }

  @FragmentScope
  @Provides
  @Named("AdapterLate")
  fun provideMainPageAdapterLate(): MainPageAdapter {
    return MainPageAdapter(mFragment.context)
  }

  @FragmentScope
  @Provides
  @Named("AdapterView")
  fun provideMainPageAdapterView(): MainPageAdapter {
    return MainPageAdapter(mFragment.context)
  }

  @FragmentScope
  @Provides
  @Named("AdapterWaiting")
  fun provideMainPageAdapterWaiting(): MainPageAdapter {
    return MainPageAdapter(mFragment.context)
  }

  @FragmentScope
  @Provides
  @Named("AdapterRead")
  fun provideMainPageAdapterRead(): MainPageAdapter {
    return MainPageAdapter(mFragment.context)
  }


  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mFragment.parentFragment)
  }

  @FragmentScope
  @Provides
  fun provideUserRepository(localDataSource: UserLocalDataSource,
      remoteDataSource: UserRemoteDataSource): UserRepository {
    return UserRepositoryImpl(remoteDataSource, localDataSource)
  }
}
