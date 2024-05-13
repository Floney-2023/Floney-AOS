package com.aos.floney.view.signup

import com.aos.floney.base.BaseViewModel
import com.aos.model.user.SocialUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : BaseViewModel() {
    private var socialUserModel: SocialUserModel? = null

    // 소셜 회원가입 데이터 저장
    fun setSocialUserModel(provider: String, token: String, email: String, nickname: String) {
        socialUserModel = SocialUserModel(
            provider, token, email, nickname
        )
    }

    // 소셜 회원가입 데이터 가져오기
    fun getSocialUserModel(): SocialUserModel? {
        return socialUserModel
    }

}