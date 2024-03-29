package com.aos.data.api

import retrofit2.http.Field
import retrofit2.http.POST

interface UserService {

    @POST
    suspend fun postRegisterUser(
        @Field("email") email: String,
        @Field("nickname") nickname: String,
        @Field("password") password: String,
        @Field("receiveMarketing") receiveMarketing: String,
    )

}