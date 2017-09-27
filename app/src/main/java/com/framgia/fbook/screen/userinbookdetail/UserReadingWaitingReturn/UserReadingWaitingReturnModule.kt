package com.framgia.fbook.screen.userinbookdetail.screen.UserReadingWaitingReturn

import android.support.v4.app.Fragment
import com.framgia.fbook.utils.dagger.FragmentScope
import com.fstyle.structure_android.widget.dialog.DialogManager
import com.fstyle.structure_android.widget.dialog.DialogManagerImpl
import dagger.Module
import dagger.Provides

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the [UserReadingWaitingReturnPresenter].
 */
@Module
class UserReadingWaitingReturnModule(private val mFragment: Fragment) {

  @FragmentScope
  @Provides
  fun providePresenter(): UserReadingWaitingReturnContract.Presenter {
    return UserReadingWaitingReturnPresenter()
  }

  @FragmentScope
  @Provides
  fun provideDialogManager(): DialogManager {
    return DialogManagerImpl(mFragment.context)
  }
}
