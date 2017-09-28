package com.framgia.fbook.data.source.remote.api.middleware

import com.framgia.fbook.data.source.TokenRepository
import com.framgia.fbook.utils.common.StringUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Created by Sun on 3/18/2017.
 */

class InterceptorImpl(private val mTokenRepository: TokenRepository) : Interceptor {

  private val KEY_TOKEN = "Authorization"

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = initializeHeader(chain)
    var request = builder.build()
    var response = chain.proceed(request)
    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
      //            refreshToken();
      //            builder.addHeader("Authorization", "Bearer " + accessToken);
      request = builder.build()
      response = chain.proceed(request)
    }
    return response
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
}
