package com.framgia.fbook.fireBase

import android.app.Service
import com.framgia.fbook.data.source.UserRepository
import com.framgia.fbook.data.source.UserRepositoryImpl
import com.framgia.fbook.data.source.local.UserLocalDataSource
import com.framgia.fbook.data.source.remote.UserRemoteDataSource
import com.framgia.fbook.utils.dagger.ServiceScope
import dagger.Module
import dagger.Provides

/**
 * Created by framgia on 11/10/2017.
 */
@Module
class FireBaseModule(private val service: Service) {

  @ServiceScope
  @Provides
  fun providerUserRepository(userRemoteDataSource: UserRemoteDataSource,
      userLocalDataSource: UserLocalDataSource): UserRepository {
    return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
  }
}
