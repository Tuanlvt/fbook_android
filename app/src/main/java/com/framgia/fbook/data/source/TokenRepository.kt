package com.framgia.fbook.data.source

import com.framgia.fbook.data.source.local.TokenLocalDataSource
import com.framgia.fbook.data.source.remote.api.response.TokenResponse
import javax.inject.Inject

/**
 * Created by ThS on 8/30/2017.
 */
interface TokenRepository : TokenDataSource.LocalDataSource

open class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource) : TokenRepository {

  override fun saveToken(token: TokenResponse.Token?) {
    tokenLocalDataSource.saveToken(token)
  }

  override fun getToken(): TokenResponse.Token? {
    return tokenLocalDataSource.getToken()
  }
}
