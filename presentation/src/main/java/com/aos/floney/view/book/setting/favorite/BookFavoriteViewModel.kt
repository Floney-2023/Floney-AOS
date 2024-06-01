package com.aos.floney.view.book.setting.favorite

import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.usecase.home.GetBookInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class BookFavoriteViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val getBookInfoUseCase: GetBookInfoUseCase
): BaseViewModel() {

    // 날짜 데이터
    private val _calendar = MutableStateFlow<Calendar>(Calendar.getInstance())

    // 현재 로그인된 계정 닉네임
    private lateinit var myNickname: String

    // 내역 추가 -> 즐겨찾기 화면 이동 여부
    var entryCheck : Boolean = false

    init{
        getBookInfo()
    }

    // 오늘 날짜 가져오기
    fun getTodayString(): String{
        val year = _calendar.value.get(Calendar.YEAR)
        val month = if ((_calendar.value.get(Calendar.MONTH) + 1) < 10) {
            "0${_calendar.value.get(Calendar.MONTH) + 1}"
        } else {
            _calendar.value.get(Calendar.MONTH) + 1
        }
        val day = if (_calendar.value.get(Calendar.DATE) < 10) {
            "0${_calendar.value.get(Calendar.DATE)}"
        } else {
            _calendar.value.get(Calendar.DATE)
        }
        return "$year.$month.$day"
    }
    private fun getBookInfo() {
        viewModelScope.launch {
            baseEvent(Event.ShowLoading)
            getBookInfoUseCase(prefs.getString("bookKey", "")).onSuccess {
                baseEvent(Event.HideLoading)
                // 내 닉네임 저장
                it.ourBookUsers.forEach {
                    if (it.me) {
                        myNickname = it.name
                    }
                }
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookFavoriteViewModel)))
            }
        }
    }
    fun setEntryPoint(entryPoint: String) {
        if (entryPoint!=""){
            entryCheck = true
        }
    }
}