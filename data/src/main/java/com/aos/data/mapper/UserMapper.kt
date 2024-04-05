package com.aos.data.mapper

import com.aos.data.entity.response.home.GetReceiveMarketingEntity
import com.aos.data.entity.response.user.PostLoginEntity
import com.aos.data.entity.response.user.PostSignUpUserEntity
import com.aos.model.user.GetReceiveMarketingModel
import com.aos.model.user.PostLoginModel
import com.aos.model.user.PostSignUpUserModel

fun PostSignUpUserEntity.toPostSignUpUserModel(): PostSignUpUserModel {
    return PostSignUpUserModel(
        this.accessToken,
        this.refreshToken,
    )
}

fun PostLoginEntity.toPostLoginModel(): PostLoginModel {
    return PostLoginModel(
        this.accessToken,
        this.refreshToken,
    )
}

fun GetReceiveMarketingEntity.toGetReceiveMarketing(): GetReceiveMarketingModel {
    return GetReceiveMarketingModel(
        this.receiveMarketing
    )
}