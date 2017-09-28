package com.framgia.fbook

import android.content.Context
import com.framgia.fbook.data.source.TokenRepository
import com.framgia.fbook.data.source.local.sharedprf.SharedPrefsApi
import com.framgia.fbook.data.source.remote.api.NetworkModule
import com.framgia.fbook.data.source.remote.api.service.FBookApi
import com.framgia.fbook.utils.dagger.AppScope
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import dagger.Component

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@AppScope
@Component(
    modules = arrayOf(ApplicationModule::class, NetworkModule::class))
interface AppComponent {

  //============== Region for Repository ================//

  fun fbookApi(): FBookApi

  fun sharedPrefsApi(): SharedPrefsApi

  //=============== Region for common ===============//

  fun applicationContext(): Context

  fun baseSchedulerProvider(): BaseSchedulerProvider

  fun tokenRepository(): TokenRepository
}
