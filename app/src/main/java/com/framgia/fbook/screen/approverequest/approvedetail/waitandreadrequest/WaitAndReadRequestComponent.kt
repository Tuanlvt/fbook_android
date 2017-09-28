package com.framgia.fbook.screen.approverequest.approvedetail.waitandreadrequest

import com.framgia.fbook.screen.approverequest.approvedetail.ApproveDetailComponent
import com.framgia.fbook.utils.dagger.FragmentScope
import dagger.Component

/**
 * This is a Dagger component. Refer to
 * [com.framgia.fbook.screen.approverequest.approvedetail.waitandreadrequest.MainApplication]
 * for the list of Dagger components
 * used in this application.
 */
@FragmentScope
@Component(dependencies = arrayOf(ApproveDetailComponent::class),
    modules = arrayOf(WaitAndReadRequestModule::class))
interface WaitAndReadRequestComponent {
  fun inject(waitandreadrequestFragment: WaitAndReadRequestFragment)
}
