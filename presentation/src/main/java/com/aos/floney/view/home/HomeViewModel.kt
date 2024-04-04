package com.aos.floney.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.home.CheckUserBookUseCase
import com.aos.usecase.home.SearchBookMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val checkUserBookUseCase: CheckUserBookUseCase
): BaseViewModel() {


    // 날짜 데이터
    private val _calendar = MutableStateFlow<Calendar>(Calendar.getInstance())
    val calendar: MutableStateFlow<Calendar> get() = _calendar

    private var _showDate = MutableLiveData<String>()
    val showDate: LiveData<String> get() = _showDate

    // 이전 월 이동
    private var _clickedPreviousMonth = MutableEventFlow<String>()
    val clickedPreviousMonth: EventFlow<String> get() = _clickedPreviousMonth

    // 다음 월 이동
    private var _clickedNextMonth = MutableEventFlow<String>()
    val clickedNextMonth: EventFlow<String> get() = _clickedNextMonth

    // 가계부 조회 후 프레그먼트 표시
    private var _showCalendarFragment = MutableEventFlow<Boolean>()
    val showCalendarFragment: EventFlow<Boolean> get() = _showCalendarFragment

    init {
        checkUserBooks()
        getFormatDate()
    }

    // 유저 가계부 유효 확인
    private fun checkUserBooks() {
        viewModelScope.launch {
            checkUserBookUseCase().onSuccess {
                prefs.setString("bookKey", it.bookKey)
                _showCalendarFragment.emit(true)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

    // 이전 월 클릭
    fun onClickPreviousMonth() {
        viewModelScope.launch {
            updateCalendarMonth(-1)
            _clickedPreviousMonth.emit(getFormatDate())
        }
    }

    // 다음 월 클릭
    fun onClickNextMonth() {
        Timber.e("onClickNextMonth")
        viewModelScope.launch {
            updateCalendarMonth(1)
            Timber.e("onClickNextMonth - emit")
            _clickedNextMonth.emit(getFormatDate())
        }
    }

    // 캘린더 값 변경
    private fun updateCalendarMonth(value: Int) {
        _calendar.value.set(Calendar.DAY_OF_MONTH, 1)
        _calendar.value.add(Calendar.MONTH, value)
    }

    // 날짜 포멧 결과 가져오기
    private fun getFormatDate(): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_calendar.value.time)
        _showDate.value = date.substring(0, 7).replace("-", ".")
        return date
    }
}