package com.aos.data.repository.remote.user

import com.aos.data.api.UserService
import com.aos.data.entity.request.user.DeleteWithdrawBody
import com.aos.data.entity.request.user.PostCheckEmailCodeBody
import com.aos.data.entity.request.user.PostCheckPasswordBody
import com.aos.data.entity.request.user.PostLoginBody
import com.aos.data.entity.request.user.PostRecentBookkeySaveBody
import com.aos.data.entity.request.user.PostSignUpUserBody
import com.aos.data.entity.request.user.PostSocialSignUpUserBody
import com.aos.data.entity.request.user.PutPasswordChangeBody
import com.aos.data.entity.response.home.GetReceiveMarketingEntity
import com.aos.data.entity.response.user.DeleteWithdrawEntity
import com.aos.data.entity.response.user.GetMypageSearchEntity
import com.aos.data.entity.response.user.PostLoginEntity
import com.aos.data.entity.response.user.PostSignUpUserEntity
import com.aos.model.user.DeleteWithdrawModel
import com.aos.util.NetworkState
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userService: UserService) :
    UserRemoteDataSource {

    // 회가입
    override suspend fun postSignUpUser(
        email: String,
        nickname: String,
        password: String,
        receiveMarketing: Boolean,
    ): NetworkState<PostSignUpUserEntity> {
        return userService.postSignUpUser(
            PostSignUpUserBody(
                email = email,
                nickname = nickname,
                password = password,
                receiveMarketing = receiveMarketing
            )
        )
    }

    override suspend fun postSocialSignUpUser(
        provider: String,
        token: String,
        email: String,
        nickname: String,
        receiveMarketing: Boolean
    ): NetworkState<PostSignUpUserEntity> {
        return userService.postSocialSignUpUser(
            provider = provider,
            token = token,
            postSocialSignUpUserBody = PostSocialSignUpUserBody(
                email, nickname, receiveMarketing
            )
        )
    }

    override suspend fun getSendEmail(email: String): NetworkState<Void> {
        return userService.getSendEmail(email)
    }

    override suspend fun postCheckEmailCode(postCheckEmailCodeBody: PostCheckEmailCodeBody): NetworkState<Void> {
        return userService.postCheckEmailCode(postCheckEmailCodeBody)
    }

    override suspend fun getSendEmailPassword(email: String): NetworkState<Void> {
        return userService.getSendTempPassword(email)
    }

    override suspend fun postLogin(email: String, password: String): NetworkState<PostLoginEntity> {
        return userService.postLogin(PostLoginBody(email, password))
    }

    override suspend fun putPasswordChange(putPasswordChangeBody : PutPasswordChangeBody): NetworkState<Void> {
        return userService.putPasswordChange(putPasswordChangeBody)
    }

    override suspend fun getNicknameChange(nickname: String): NetworkState<Void> {
        return userService.getNicknameChange(nickname)
    }

    override suspend fun putMarketingChange(agree: Boolean): NetworkState<Void> {
        return userService.putMarketingChange(agree)
    }
    override suspend fun getMarketingCheck(): NetworkState<GetReceiveMarketingEntity> {
        return userService.getMarketingCheck()
    }
    override suspend fun getMypageSearch(): NetworkState<GetMypageSearchEntity> {
        return userService.getMypageSearch()
    }

    override suspend fun getLogout(accessToken: String): NetworkState<Void> {
        return userService.getLogout(accessToken)
    }
    override suspend fun deleteWithdraw(accessToken: String, deleteWithdrawBody: DeleteWithdrawBody): NetworkState<DeleteWithdrawEntity> {
        return userService.deleteWithdraw(accessToken,deleteWithdrawBody)
    }
    override suspend fun postCheckPassword(postCheckPasswordBody: PostCheckPasswordBody): NetworkState<Void> {
        return userService.postCheckPassword(postCheckPasswordBody)
    }
    override suspend fun postRecentBookkeySave(postRecentBookkeySaveBody: PostRecentBookkeySaveBody): NetworkState<Void> {
        return userService.postRecentBookkeySave(postRecentBookkeySaveBody)
    }

    override suspend fun getAuthTokenCheck(provider: String, token: String): NetworkState<Boolean> {
        return userService.getAuthTokenCheck(provider, token)
    }
}