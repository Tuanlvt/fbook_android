package com.framgia.fbook.data.source.remote.api.middleware

import android.util.Log
import com.framgia.fbook.data.source.TokenRepository
import com.framgia.fbook.data.source.remote.api.error.BaseException
import com.framgia.fbook.data.source.remote.api.request.RefreshTokenRequest
import com.framgia.fbook.utils.common.StringUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Created by Sun on 3/18/2017.
 */

class InterceptorImpl : Interceptor {

  private val TAG = javaClass.name
  private val KEY_TOKEN = "Authorization"

  private lateinit var mTokenRepository: TokenRepository

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = initializeHeader(chain)
    var request = builder.build()
    var response = chain.proceed(request)
    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
      refreshToken()
      builder.removeHeader(KEY_TOKEN)
      builder.addHeader(KEY_TOKEN, getAccessToken())
      request = builder.build()
      response = chain.proceed(request)
    }
    return response
  }

  fun setTokenRepository(tokenRepository: TokenRepository) {
    mTokenRepository = tokenRepository
  }

  private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
    val originRequest = chain.request()
    return originRequest.newBuilder()
        .header("Accept", "application/json")
        .addHeader("Cache-Control", "no-cache")
        .addHeader("Cache-Control", "no-store")
        .addHeader(KEY_TOKEN, getAccessToken())
        .method(originRequest.method(), originRequest.body())
  }

  private fun getAccessToken(): String? {
    var accessToken = mTokenRepository.getToken()?.accessToken
    if (StringUtils.isBlank(accessToken)) {
      accessToken = ""
    }
    return accessToken
  }

  private fun refreshToken() {
    val tokenRequest = RefreshTokenRequest()
    tokenRequest.refresh_token = mTokenRepository.getToken()?.refreshToken

    mTokenRepository.refreshToken(tokenRequest)
        .subscribe(
            { tokenResponse ->
              mTokenRepository.saveToken(tokenResponse.token)
            },
            { error ->
              Log.e(TAG, (error as BaseException).getMessageError())
            })
  }
}
