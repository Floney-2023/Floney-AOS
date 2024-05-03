package com.aos.data.util

import com.aos.data.BuildConfig
import com.aos.data.entity.response.token.PostUserReissueEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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
import java.io.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val prefs: SharedPreferenceUtil
) : okhttp3.Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val originRequest = response.request

        if (originRequest.header("Authorization").isNullOrEmpty()) {
            return null
        }


        // CoroutineScope 내에서 실행하여 비동기적으로 토큰 재발급
        val refreshRequest = Request.Builder()
            .url("${BuildConfig.BASE_URL}users/reissue")
            .post(createTokenReissueRequestBody())
            .build()

        try {
            val refreshedToken = executeRefreshTokenRequest(refreshRequest)
            return refreshedToken?.let {
                updateTokenInPrefs(it.accessToken, it.refreshToken)

                originRequest.newBuilder().header("Authorization", "Bearer ${it.accessToken}").build()
            }
        } catch (e: IOException) {
            Timber.e(e, "Failed to refresh token")
        }

        // 새로운 요청을 기다리지 않고 null 반환
        return null
    }

    private fun executeRefreshTokenRequest(refreshRequest: Request): PostUserReissueEntity? {
        val response = OkHttpClient().newCall(refreshRequest).execute()
        return response.use {
            if (response.isSuccessful) {
                val json = Json { ignoreUnknownKeys = true }
                val responseBody = response.body?.string()
                responseBody?.let {
                    json.decodeFromString<PostUserReissueEntity>(it)
                }
            } else {
                clearTokens()
                null
            }
        }
    }

    private fun updateTokenInPrefs(accessToken: String, refreshToken: String) {
        prefs.setString("accessToken", accessToken)
        prefs.setString("refreshToken", refreshToken)
    }

    private fun clearTokens() {
        prefs.setString("accessToken", "")
        prefs.setString("refreshToken", "")
    }

    private fun createTokenReissueRequestBody(): RequestBody {
        val requestBodyString = """
        {
            "accessToken": "${prefs.getString("accessToken", "")}",
            "refreshToken": "${prefs.getString("refreshToken", "")}"
        }
    """.trimIndent()
        Timber.e("requestBodyString $requestBodyString")

        return requestBodyString.toRequestBody("application/json".toMediaTypeOrNull())
    }
}