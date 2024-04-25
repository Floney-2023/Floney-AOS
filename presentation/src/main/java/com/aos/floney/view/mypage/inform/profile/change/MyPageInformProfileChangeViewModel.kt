package com.aos.floney.view.mypage.inform.profile.change

import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageInformProfileChangeViewModel @Inject constructor(): BaseViewModel() {

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 이전 페이지로 이동
    fun onClickPreviousPage()
    {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 프로필 이미지 설정
    fun onClickSettingImage()
    {

    }

    // 기본 이미지로 설정
    fun onClickBasicSettingImage()
    {

    }

    // 변경하기 버튼 클릭
    fun onClickProfileChange()
    {

    }

}