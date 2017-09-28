package com.framgia.fbook.screen.approverequest.approvedetail;

import com.framgia.fbook.AppComponent
import com.framgia.fbook.utils.dagger.ActivityScope
import dagger.Component

/**
 * This is a Dagger component. Refer to {@link com.framgia.fbook.screen.approverequest.approvedetail.MainApplication} for the list of Dagger components
 * used in this application.
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class),
    modules = arrayOf(ApproveDetailModule::class))
interface ApproveDetailComponent {
  fun inject(approvedetailActivity: ApproveDetailActivity)
}
