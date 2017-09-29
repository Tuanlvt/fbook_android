package com.framgia.fbook.data.source

import com.framgia.fbook.data.source.local.TokenLocalDataSource
import com.framgia.fbook.data.source.remote.TokenRemoteDataSource
import com.framgia.fbook.data.source.remote.api.request.RefreshTokenRequest
import com.framgia.fbook.data.source.remote.api.response.TokenResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by ThS on 8/30/2017.
 */
interface TokenRepository : TokenDataSource.LocalDataSource, TokenDataSource.RemoteDataSource

open class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenRemoteDataSource: TokenRemoteDataSource) : TokenRepository {
  override fun saveToken(token: TokenResponse.Token?) {
    tokenLocalDataSource.saveToken(token)
  }

  override fun getToken(): TokenResponse.Token? {
    return tokenLocalDataSource.getToken()
  }

  override fun refreshToken(refreshTokenRequest: RefreshTokenRequest?): Single<TokenResponse> {
    return tokenRemoteDataSource.refreshToken(refreshTokenRequest)
  }
}
