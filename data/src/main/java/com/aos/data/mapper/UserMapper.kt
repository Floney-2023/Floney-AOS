package com.aos.data.mapper

import com.aos.data.entity.response.user.PostSignUpUserEntity
import com.aos.model.PostSignUpUserModel

fun PostSignUpUserEntity.toPostSignUpUserModel(): PostSignUpUserModel {
    return PostSignUpUserModel(
        this.accessToken,
        this.refreshToken,
    )
}