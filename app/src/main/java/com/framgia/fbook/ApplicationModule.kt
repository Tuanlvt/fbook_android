package com.framgia.fbook

import android.content.Context
import com.framgia.fbook.data.source.TokenRepository
import com.framgia.fbook.data.source.TokenRepositoryImpl
import com.framgia.fbook.data.source.local.TokenLocalDataSource
import com.framgia.fbook.data.source.local.sharedprf.SharedPrefsApi
import com.framgia.fbook.data.source.local.sharedprf.SharedPrefsImpl
import com.framgia.fbook.data.source.remote.TokenRemoteDataSource
import com.framgia.fbook.data.source.remote.api.middleware.InterceptorImpl
import com.framgia.fbook.utils.dagger.AppScope
import com.framgia.fbook.utils.rx.BaseSchedulerProvider
import com.framgia.fbook.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor

/**
 * Created by le.quang.dao on 21/03/2017.
 */

@Module
class ApplicationModule(private val mContext: Context) {

  @Provides
  @AppScope
  fun provideApplicationContext(): Context {
    return mContext
  }

  @Provides
  @AppScope
  fun provideSharedPrefsApi(): SharedPrefsApi {
    return SharedPrefsImpl(mContext)
  }

  @Provides
  @AppScope
  fun provideBaseSchedulerProvider(interceptor: Interceptor,
      tokenRepository: TokenRepository): BaseSchedulerProvider {
    (interceptor as InterceptorImpl).setTokenRepository(tokenRepository)
    return SchedulerProvider.instance
  }

  @Provides
  @AppScope
  fun provideTokenRepository(localDataSource: TokenLocalDataSource,
      remoteDataSource: TokenRemoteDataSource): TokenRepository {
    return TokenRepositoryImpl(localDataSource, remoteDataSource)
  }
}
