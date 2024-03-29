package com.aos.floney.view.login

import androidx.lifecycle.MutableLiveData
import com.aos.floney.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): BaseViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    // 로그인하기 클릭
    fun onClickLogin() {

    }

    // 비밀번호 찾기 클릭
    fun onClickFindPassword() {

    }

    // 회원가입 클릭
    fun onClickSignUp() {

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