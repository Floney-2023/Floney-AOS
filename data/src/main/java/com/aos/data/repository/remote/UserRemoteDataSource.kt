package com.aos.data.repository.remote

import com.aos.data.entity.response.PostSignUpUserEntity
import com.aos.util.NetworkState

interface UserRemoteDataSource {

    suspend fun postSignUpUser(
        email: String,
        nickname: String,
        password: String,
        receiveMarketing: Boolean,
    ): NetworkState<PostSignUpUserEntity>

}