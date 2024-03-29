package com.aos.data.api

import com.aos.data.entity.response.PostSignUpUserEntity
import com.aos.util.NetworkState
import retrofit2.http.Field
import retrofit2.http.POST

interface UserService {

    @POST
    suspend fun postSignUpUser(
        @Field("email") email: String,
        @Field("nickname") nickname: String,
        @Field("password") password: String,
        @Field("receiveMarketing") receiveMarketing: Boolean,
    ): NetworkState<PostSignUpUserEntity>

}