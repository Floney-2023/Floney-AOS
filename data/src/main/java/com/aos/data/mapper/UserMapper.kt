package com.aos.data.mapper

import com.aos.data.entity.response.home.GetReceiveMarketingEntity
import com.aos.data.entity.response.user.DeleteWithdrawEntity
import com.aos.data.entity.response.user.GetMypageSearchEntity
import com.aos.data.entity.response.user.PostLoginEntity
import com.aos.data.entity.response.user.PostSignUpUserEntity
import com.aos.model.user.DeleteWithdrawModel
import com.aos.model.user.UiMypageSearchModel
import com.aos.model.user.GetReceiveMarketingModel
import com.aos.model.user.PostLoginModel
import com.aos.model.user.PostSignUpUserModel
import com.aos.model.user.MyBooks

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

fun GetMypageSearchEntity.toUiMypageSearchModel(): UiMypageSearchModel {

    val myBooks = this.myBooks.map {
        MyBooks(
            bookImg = it.bookImg,
            bookKey = it.bookKey,
            name = it.name,
            memberCount = it.memberCount,
            recentCheck = false
        )
    }
    return UiMypageSearchModel(
        nickname = this.nickname,
        email = this.email,
        profileImg = this.profileImg,
        provider = this.provider,
        lastAdTime = this.lastAdTime,
        myBooks = myBooks
    )
}

fun DeleteWithdrawEntity.toDeleteWithdrawModel() : DeleteWithdrawModel {
    return DeleteWithdrawModel(
        this.deletedBookKeys,
        this.otherBookKeys
    )
}