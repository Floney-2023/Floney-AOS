package com.aos.data.mapper

import com.aos.data.entity.response.PostSignUpUserEntity
import com.aos.model.PostSignUpUserModel

fun PostSignUpUserEntity.toPostSignUpUserModel(): PostSignUpUserModel {
    return PostSignUpUserModel(
        this.accessToken,
        this.refreshToken,
    )
}