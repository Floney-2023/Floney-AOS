package com.aos.data.util

import com.aos.data.BuildConfig
import com.aos.data.entity.response.token.PostUserReissueEntity
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val prefs: SharedPreferenceUtil
): okhttp3.Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val originRequest = response.request

        if(originRequest.header("Authorization").isNullOrEmpty()){
            return null
        }

        val refreshToken = runBlocking {
            prefs.getString("refreshToken", "")
        }
        //재발급 api 요청하기
        val refreshRequest = Request.Builder()
            .url("${BuildConfig.BASE_URL}users/reissue")
            .post(createTokenReissueRequestBody())
            .addHeader("authorization", "Bearer ${refreshToken!!}")
            .build()

        OkHttpClient().newCall(refreshRequest).execute().use {response ->
            Timber.e("response.code ${response.code}")
            if(response.code == 201) {
                val json = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }

                val responseBody = response.body?.string()
                var token = PostUserReissueEntity("", "")
                responseBody?.let {
                    token = json.decodeFromString<PostUserReissueEntity>(responseBody)
                    Timber.e("토큰 $token")
                    prefs.setString("accessToken", token.accessToken)
                    prefs.setString("refreshToken", token.refreshToken)
                }
                response.close()
                return originRequest.newBuilder().header("Authorization", "Bearer ${token.accessToken}").build()
            } else {
                response.close()
                prefs.setString("accessToken", "")
                prefs.setString("refreshToken", "")
                return null
            }
        }
    }

    private fun createTokenReissueRequestBody(): RequestBody {
        val requestBodyString = """
        {
            "accessToken": "${getAccessToken()}",
            "refreshToken": "${getRefreshToken()}"
        }
    """.trimIndent()

        return requestBodyString.toRequestBody("application/json".toMediaTypeOrNull())
    }

    private fun getAccessToken(): String {
        return prefs.getString("accessToken", "")
    }

    private fun getRefreshToken(): String {
        return prefs.getString("refreshToken", "")
    }
}