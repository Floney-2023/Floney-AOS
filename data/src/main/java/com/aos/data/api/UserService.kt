package com.aos.data.api

import com.aos.data.entity.request.user.DeleteWithdrawBody
import com.aos.data.entity.request.user.PostCheckEmailCodeBody
import com.aos.data.entity.request.user.PostCheckPasswordBody
import com.aos.data.entity.request.user.PostLoginBody
import com.aos.data.entity.request.user.PostSignUpUserBody
import com.aos.data.entity.request.user.PutPasswordChangeBody
import com.aos.data.entity.response.home.GetReceiveMarketingEntity
import com.aos.data.entity.response.user.DeleteWithdrawEntity
import com.aos.data.entity.response.user.GetMypageSearchEntity
import com.aos.data.entity.response.user.PostLoginEntity
import com.aos.data.entity.response.user.PostSignUpUserEntity
import com.aos.model.user.DeleteWithdrawModel
import com.aos.util.NetworkState
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService {

    // 회원가입
    @POST("users")
    @Headers("Auth: false")
    suspend fun postSignUpUser( 
        @Body postSignUpUserBody: PostSignUpUserBody
    ): NetworkState<PostSignUpUserEntity>

    // 이메일 전송
    @GET("users/email/mail")
    @Headers("Auth: false")
    suspend fun getSendEmail(
        @Query("email") email: String
    ): NetworkState<Void>

    // 이메일 코드 검사
    @POST("users/email/mail")
    @Headers("Auth: false")
    suspend fun postCheckEmailCode(
        @Body postCheckEmailCodeBody: PostCheckEmailCodeBody
    ): NetworkState<Void>

    // 임시 비밀번호 발송
    @GET("users/password/find")
    @Headers("Auth: false")
    suspend fun getSendTempPassword(
        @Query("email") email: String
    ): NetworkState<Void>

    // 로그인
    @POST("users/login")
    @Headers("Auth: false")
    suspend fun postLogin(
        @Body postLoginBody: PostLoginBody
    ): NetworkState<PostLoginEntity>

    // 비밀번호 변경
    @PUT("users/password")
    @Headers("Auth: true")
    suspend fun putPasswordChange(
        @Body putPasswordChangeBody: PutPasswordChangeBody
    ): NetworkState<Void>

    // 닉네임 변경
    @GET("users/nickname/update")
    @Headers("Auth: true")
    suspend fun getNicknameChange(
        @Query("nickname") nickname: String
    ): NetworkState<Void>

    // 유저 마케팅 수신 동의 여부 변경
    @PUT("users/receive-marketing")
    @Headers("Auth: true")
    suspend fun putMarketingChange(
        @Query("agree") agree: Boolean
    ): NetworkState<Void>

    // 유저 마케팅 수신 동의 여부 확인
    @GET("users/receive-marketing")
    @Headers("Auth: true")
    suspend fun getMarketingCheck(): NetworkState<GetReceiveMarketingEntity>

    // 마이페이지 조회
    @GET("users/mypage")
    @Headers("Auth: true")
    suspend fun getMypageSearch(): NetworkState<GetMypageSearchEntity>

    // 로그아웃
    @GET("users/logout")
    @Headers("Auth: false")
    suspend fun getLogout(
        @Query("accessToken") accessToken: String
    ): NetworkState<Void>

    // 회원탈퇴
    @HTTP(method = "DELETE", path="users", hasBody = true)
    @Headers("Auth: false")
    suspend fun deleteWithdraw(
        @Query("accessToken") accessToken: String,
        @Body deleteWithdrawBody: DeleteWithdrawBody
    ): NetworkState<DeleteWithdrawEntity>

    // 유저 비밀번호 검사
    @POST("users/password")
    @Headers("Auth: true")
    suspend fun postCheckPassword(
        @Body postCheckPasswordBody: PostCheckPasswordBody
    ): NetworkState<Void>
}