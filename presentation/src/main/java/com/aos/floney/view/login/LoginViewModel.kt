package com.aos.floney.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aos.floney.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): BaseViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    // 회원가입 클릭
    private var _clickSignUp = MutableLiveData<Boolean>()
    val clickSignUp: LiveData<Boolean> get() = _clickSignUp

    // 로그인하기 클릭
    fun onClickLogin() {

    }

    // 비밀번호 찾기 클릭
    fun onClickFindPassword() {

    }

    // 회원가입 클릭
    fun onClickSignUp() {
        Timber.e("onClickSignUp")

        _clickSignUp.value = true
    }

    // 카카오 로그인 클릭
    fun onClickKakaoLogin() {

    }

    // 구글 로그인 클릭
    fun onClickGoogleLogin() {

    }

    // 애플 로그인 클릭
    fun onClickAppleLogin() {

    }

}