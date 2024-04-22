package com.aos.floney.view.book.setting.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSettingProfileChangeViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): BaseViewModel() {

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    var profileImg: LiveData<String> = stateHandle.getLiveData("profileImg")

    // 이전 페이지로 이동
    fun onClickPreviousPage()
    {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 이미지 뷰 클릭 -> 프로필 이미지 설정
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