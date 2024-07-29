package com.aos.floney.view.signup

import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.user.SocialUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : BaseViewModel() {
    private var socialUserModel: SocialUserModel? = null

    private var _onClickedBack = MutableEventFlow<Boolean>()
    val onCLickBack: EventFlow<Boolean> get() = _onClickedBack

    // 소셜 회원가입 데이터 저장
    fun setSocialUserModel(provider: String, token: String, email: String, nickname: String) {
        Timber.e("provider $provider")
        Timber.e("token $token")
        Timber.e("email $email")
        Timber.e("nickname $nickname")
        socialUserModel = SocialUserModel(
            provider, token, email, nickname
        )
    }

    // 소셜 회원가입 데이터 가져오기
    fun getSocialUserModel(): SocialUserModel? {
        return socialUserModel
    }

    fun onClickedBack() {
        viewModelScope.launch {
            _onClickedBack.emit(true)
        }
    }
}