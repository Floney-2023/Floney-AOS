package com.aos.floney.view.mypage.setting.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.mypage.NicknameChangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageSettingMainViewModel @Inject constructor(): BaseViewModel() {

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 바뀔 닉네임
    var nickName = MutableLiveData<String>("")

    // 알림 변경 페이지 이동
    private var _alarmSettingPage = MutableEventFlow<Boolean>()
    val alarmSettingPage: EventFlow<Boolean> get() = _alarmSettingPage


    // 언어 설정 페이지 이동
    private var _langaugeSettingPage = MutableEventFlow<Boolean>()
    val langaugeSettingPage: EventFlow<Boolean> get() = _langaugeSettingPage


    // 이전 페이지로 이동
    fun onClickPreviousPage()
    {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
    // 알림 설정 페이지 이동
    fun onClickAlarmSettingPage()
    {
        viewModelScope.launch {
            _alarmSettingPage.emit(true)
        }
    }

    // 연어 설정 페이지 이동
    fun onClickLanguageSettingPage()
    {
        viewModelScope.launch {
            _langaugeSettingPage.emit(true)
        }
    }

}