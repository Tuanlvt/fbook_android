package com.framgia.fbook.data.source

import com.framgia.fbook.data.source.remote.api.request.RefreshTokenRequest
import com.framgia.fbook.data.source.remote.api.response.TokenResponse
import io.reactivex.Single

/**
 * Created by ThS on 8/30/2017.
 */
interface TokenDataSource {
  interface LocalDataSource {
    fun saveToken(token: TokenResponse.Token?)

    fun getToken(): TokenResponse.Token?
  }

  interface RemoteDataSource {
    fun refreshToken(refreshTokenRequest: RefreshTokenRequest?): Single<TokenResponse>
  }
}
