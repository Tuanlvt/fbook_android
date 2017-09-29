package com.framgia.fbook.data.source.remote

import com.framgia.fbook.data.source.TokenDataSource
import com.framgia.fbook.data.source.remote.api.request.RefreshTokenRequest
import com.framgia.fbook.data.source.remote.api.response.TokenResponse
import com.framgia.fbook.data.source.remote.api.service.FBookApi
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by framgia on 28/09/2017.
 */
class TokenRemoteDataSource @Inject
constructor(nameApi: FBookApi) : BaseRemoteDataSource(nameApi), TokenDataSource.RemoteDataSource {
  override fun refreshToken(refreshTokenRequest: RefreshTokenRequest?): Single<TokenResponse> {
    return fbookApi.refreshToken(refreshTokenRequest)
  }
}
