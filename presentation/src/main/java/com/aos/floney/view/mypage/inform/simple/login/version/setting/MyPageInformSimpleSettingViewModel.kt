package com.aos.floney.view.mypage.inform.simple.login.version.setting

import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageInformSimpleSettingViewModel @Inject constructor(): BaseViewModel() {


    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 이전 페이지로 이동
    fun onClickPreviousPage()
    {

    }
    // 프로필 변경 페이지 이동
    fun onClickProfileImgChange()
    {

    }
    // 비밀번호 변경
    fun onClickPasswordChange()
    {

    }
    // 로그아웃
    fun onClickLogOut()
    {

    }
    // 회원탈퇴
    fun onClickWithdrawal()
    {

    }

}