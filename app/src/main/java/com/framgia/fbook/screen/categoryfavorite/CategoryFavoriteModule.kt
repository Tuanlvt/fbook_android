package com.framgia.fbook.screen.categoryfavorite

import android.support.v4.app.Fragment
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.utils.dagger.FragmentScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the [CategoryFavoritePresenter].
 */
@Module
class CategoryFavoriteModule(private val mFragment: Fragment) {

  @FragmentScope
  @Provides
  internal fun providePresenter(userRepository: UserRepository,
      baseSchedulerProvider: BaseSchedulerProvider): CategoryFavoriteContract.Presenter {
    val presenter = CategoryFavoritePresenter(userRepository)
    presenter.setBaseSchedulerProvider(baseSchedulerProvider)
    presenter.setViewModel(mFragment as CategoryFavoriteContract.ViewModel)
    return presenter
  }

  @FragmentScope
  @Provides
  fun provideCategoryAdapter(): CategoryAdapter {
    return CategoryAdapter(mFragment.context)
  }

  @FragmentScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(mFragment)
  }

  @FragmentScope
  @Provides
  fun providerUserRepository(userRemoteDataSource: UserRemoteDataSource,
      userLocalDataSource: UserLocalDataSource): UserRepository {
    return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
  }

  @FragmentScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mFragment.context)
  }
}
