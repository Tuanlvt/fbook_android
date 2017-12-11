package com.framgia.fbook.screen.updatebook;

import android.app.Activity;
import android.content.Context
import com.framgia.fbook.data.source.*
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.BookRemoteDataSource
import com.framgia.fbook.data.source.remote.CategoryRemoteDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.screen.login.LoginActivity
import com.framgia.fbook.utils.dagger.ActivityScope
import com.framgia.fbook.utils.navigator.Navigator
import com.framgia.fbook.utils.validator.Validator
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link UpdateBookPresenter}.
 */
@Module
class UpdateBookModule(private val activity: Activity) {

  @ActivityScope
  @Provides
  fun providePresenter(validator: Validator): UpdateBookContract.Presenter {
    val presenter = UpdateBookPresenter(validator)
    presenter.setViewModel(activity as UpdateBookContract.ViewModel)
    return presenter
  }

  @ActivityScope
  @Provides
  fun provideNavigator(): Navigator {
    return Navigator(activity)
  }

  @ActivityScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(activity)
  }

  @ActivityScope
  @Provides
  fun provideValidator(context: Context): Validator {
    return Validator(context, LoginActivity::class.java)
  }
}
