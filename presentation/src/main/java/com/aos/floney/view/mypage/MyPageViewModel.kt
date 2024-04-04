package com.aos.floney.view.mypage

import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(): BaseViewModel() {


    // 회원 정보 페이지
    private var _informPage = MutableEventFlow<Boolean>()
    val informPage: EventFlow<Boolean> get() = _informPage

    // 알람 페이지 이동
    fun onClickAlarmPage()
    {

    }

    // 설정 페이지 이동
    fun onClickSettingPage()
    {

    }

    // 회원 정보 페이지 이동
    fun onClickInformPage()
    {
        viewModelScope.launch {
            _informPage.emit(true)
        }
    }

    // 문의 하기 페이지 이동
    fun onClickAnswerPage()
    {

    }

    // 공지 사항 페이지 이동
    fun onClickNoticePage()
    {

    }

    // 리뷰 작성하기 페이지 이동
    fun onClickReviewPage()
    {

    }

    // 개인 정보 처리방침 페이지 이동
    fun onClickPrivateRolePage()
    {

    }

    // 이용 약관 페이지 이동
    fun onClickUsageRightPage()
    {

    }
}