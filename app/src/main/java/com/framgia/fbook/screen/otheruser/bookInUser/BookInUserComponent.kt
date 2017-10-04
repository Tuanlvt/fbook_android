package com.framgia.fbook.screen.otheruser.bookInUser

import com.framgia.fbook.screen.otheruser.OtherUserComponent
import com.framgia.fbook.utils.dagger.FragmentScope
import dagger.Component

/**
 * This is a Dagger component. Refer to [com.framgia.fbook.MainApplication] for the list of
 * Dagger components
 * used in this application.
 */
@FragmentScope
@Component(dependencies = arrayOf(OtherUserComponent::class),
    modules = arrayOf(BookInUserModule::class))
interface BookInUserComponent {
  fun inject(bookInUserFragment: BookInUserFragment)
}
