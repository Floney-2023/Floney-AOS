package com.aos.data.util

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val prefs: SharedPreferenceUtil
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.e("chain.request().headers()[\"Auth\"] ${chain.request().headers["Auth"]}")
        if(chain.request().headers["Auth"] == "false"){

            val newRequest = chain.request().newBuilder()
                .removeHeader("Auth")
                .build()
            return chain.proceed(newRequest)
        }

        var token = ""
        runBlocking {
            token = ("Bearer " + prefs.getString("accessToken", ""))
        }
        Timber.e("token $token")
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        val response = chain.proceed(newRequest)


        return response
    }
}