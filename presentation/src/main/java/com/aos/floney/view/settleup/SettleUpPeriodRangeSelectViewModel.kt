package com.aos.floney.view.settleup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.settlement.PeriodCalendar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettleUpPeriodRangeSelectViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): BaseViewModel() {

    // 날짜 데이터
    private val _calendar = MutableStateFlow<Calendar>(Calendar.getInstance())

    // 달력 헤더 날짜
    private var _showDate = MutableLiveData<String>()
    val showDate: LiveData<String> get() = _showDate

    // 시작 날짜
    val _startDate = MutableLiveData<Calendar?>(null)
    val startDate: LiveData<Calendar?> get() = _startDate

    // 끝 날짜
    val _endDate = MutableLiveData<Calendar?>(null)
    val endDate: LiveData<Calendar?> get() = _endDate

    private var _startDateFormat = MutableLiveData<String?>(null)
    val startDateFormat: LiveData<String?> get() = _startDateFormat

    private var _endDateFormat = MutableLiveData<String?>(null)
    val endDateFormat: LiveData<String?> get() = _endDateFormat

    // 이전 월 이동
    private var _clickedPreviousMonth = MutableEventFlow<String>()
    val clickedPreviousMonth: EventFlow<String> get() = _clickedPreviousMonth

    // 다음 월 이동
    private var _clickedNextMonth = MutableEventFlow<String>()
    val clickedNextMonth: EventFlow<String> get() = _clickedNextMonth

    private var _getCalendarList = MutableLiveData<List<PeriodCalendar>>()
    val getCalendarList: LiveData<List<PeriodCalendar>> get() = _getCalendarList

    // 선택 하기 버튼 클릭
    private var _selectButton = MutableEventFlow<String?>()
    val selectButton: EventFlow<String?> get() = _selectButton

    init {
        getInformDateMonth()
        getFormatDateMonth()
    }
    // 날짜 정보 얻기
    fun getInformDateMonth(){
        _calendar.value.set(Calendar.DAY_OF_MONTH, 1)
        generateCalendarDates()
        _getCalendarList.postValue(_getCalendarList.value)
    }

    // 이전 월 클릭
    fun onClickPreviousMonth() {
        viewModelScope.launch {
            updateCalendarMonth(-1)
            generateCalendarDates() // 날짜 생성
            updateAdjustCalendar() // 값 조정
            _clickedPreviousMonth.emit(getFormatDateMonth())

        }
    }

    // 다음 월 클릭
    fun onClickNextMonth() {
        viewModelScope.launch {
            updateCalendarMonth(1)
            generateCalendarDates()
            updateAdjustCalendar()
            _clickedPreviousMonth.emit(getFormatDateMonth())
        }
    }

    // 캘린더 값 변경
    private fun updateCalendarMonth(value: Int) {
        _calendar.value.set(Calendar.DAY_OF_MONTH, 1)
        _calendar.value.add(Calendar.MONTH, value)
    }

    private fun generateCalendarDates() {
        // _calendar의 현재 상태를 저장
        val originalCalendar = _calendar.value.clone() as Calendar

        val periodCalendars = mutableListOf<PeriodCalendar>().apply {
            // 이전 달 데이터 리스트
            adjustToStartOfWeek(_calendar.value.clone() as Calendar)?.let { previousCalendars ->
                addAll(previousCalendars)
            }
            // 현재 달 데이터 리스트
            addAll(adjustNowCalendar(_calendar.value))

            // 다음 달 데이터 리스트
            adjustToEndOfWeek(_calendar.value.clone() as Calendar)?.let { nextCalendars ->
                addAll(nextCalendars)
            }
        }

        // 작업 완료 후 _calendar를 원래 상태로 복원
        _calendar.value = originalCalendar
        _getCalendarList.value = periodCalendars

    }
    private fun generatePeriodCalendar(calendar: Calendar, isMonth: Boolean, isClick: Boolean, isRightRange : Boolean, isLeftRange : Boolean): PeriodCalendar {
        return PeriodCalendar(
            year = calendar.get(Calendar.YEAR),
            month = calendar.get(Calendar.MONTH) + 1, // Calendar.MONTH는 0부터 시작하므로 +1
            day = calendar.get(Calendar.DAY_OF_MONTH),
            isMonth = isMonth,
            isClick = isClick,
            isRightRange = isRightRange,
            isLeftRange = isLeftRange
        )
    }

    private fun adjustToStartOfWeek(calendar: Calendar): MutableList<PeriodCalendar> {

        val periodCalendars = mutableListOf<PeriodCalendar>()

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -1)
            periodCalendars.add(generatePeriodCalendar(calendar, false, false, false, false))
        }

        return periodCalendars.asReversed()
    }

    private fun adjustToEndOfWeek(calendar: Calendar): MutableList<PeriodCalendar> {
        val periodCalendars = mutableListOf<PeriodCalendar>()
        // 해당 날짜가 속한 주의 토요일까지 이동하지만, 다음 달로 넘어가지 않도록 확인
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            periodCalendars.add(generatePeriodCalendar(calendar, false, false, false, false))
            calendar.add(Calendar.DATE, 1)
        }
        return periodCalendars
    }

    private fun adjustNowCalendar(calendar: Calendar): MutableList<PeriodCalendar> {
        val periodCalendars = mutableListOf<PeriodCalendar>()

        val currentMonth = calendar.get(Calendar.MONTH)

        while (calendar.get(Calendar.MONTH) == currentMonth) {
            periodCalendars.add(generatePeriodCalendar(calendar, true, false, false, false))
            calendar.add(Calendar.DATE, 1)
        }
        return periodCalendars
    }

    // 날짜 포멧 결과 가져오기
    fun getFormatDateMonth(): String {
        val date = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(_calendar.value.time)
        val showDate = date.substring(0, 7).replace("-", ".")
        _showDate.postValue(showDate)
        return date
    }
    fun updateAdjustPeriod(item: PeriodCalendar) {
        val clickItem = Calendar.getInstance().apply {
            set(item.year, item.month - 1, item.day) // 월은 0부터 시작하므로 -1 해줍니다.
        }

        if (_startDate.value != null && _endDate.value != null) {
            // startDate와 endDate가 모두 존재하는 경우
            _startDate.value = clickItem
            _endDate.value = null // endDate를 리셋합니다.
        } else if (_startDate.value != null && clickItem.before(_startDate.value)) {
            // startDate가 존재하고, item이 startDate보다 이전 날짜인 경우
            _endDate.value = _startDate.value // startDate를 endDate로 설정합니다.
            _startDate.value = clickItem
        } else if (_startDate.value != null && clickItem.after(_startDate.value)) {
            // startDate가 존재하고, item이 startDate보다 이후 날짜인 경우
            _endDate.value = clickItem
        } else if (_startDate.value != null && areCalendarsEqual(clickItem,_startDate.value!!)) {
            // startDate가 존재하고, 클릭된 item이 같은 startDate인 경우.
            _startDate.value = null
        } else {
            // startDate가 존재하지 않는 경우
            _startDate.value = clickItem
        }
        updateAdjustCalendar()

    }
    fun updateAdjustCalendar() {
        val updatedList = _getCalendarList.value?.map { calendarItem ->
            val check = Calendar.getInstance().apply {
                set(calendarItem.year, calendarItem.month - 1, calendarItem.day) // 월은 0부터 시작하므로 -1 해줍니다.
            }
            if (_startDate.value != null && _endDate.value != null) {
                if (areCalendarsEqual(check, _startDate.value!!)) {
                    calendarItem.copy(isClick = true, isRightRange = true, isLeftRange = false)
                } else if (areCalendarsEqual(check, _endDate.value!!)) {
                    calendarItem.copy(isClick = true, isRightRange = false, isLeftRange = true)
                } else if (check.after(_startDate.value) && check.before(_endDate.value)) {
                    calendarItem.copy(isRightRange = true, isLeftRange = true, isClick = false)
                } else {
                    calendarItem.copy(isRightRange = false, isLeftRange = false, isClick = false)
                }
            } else if (_startDate.value != null && areCalendarsEqual(check, _startDate.value!!)) {
                calendarItem.copy(isClick = true)
            } else {
                calendarItem.copy(isClick = false, isRightRange = false, isLeftRange = false)
            }
        }
        return _getCalendarList.postValue(updatedList!!)
    }
    fun areCalendarsEqual(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }
    fun calendarToDateString(calendar: Calendar): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return String.format("%d.%02d.%02d", year, month, day)
    }

    fun getFormatPeriodDay(calendar: Calendar): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        return date
    }

    // 날짜 선택 완료
    fun onClickPeriodSelect(){
        settingFormatDate()
        viewModelScope.launch {
            val startDateString = startDate.value?.let { calendarToDateString(it) } ?: ""
            val endDateString = endDate.value?.let { calendarToDateString(it) } ?: ""

            val displayString = when {
                startDateString.isNotEmpty() && endDateString.isNotEmpty() -> {
                    if (startDate.value!!.get(Calendar.YEAR) == endDate.value!!.get(Calendar.YEAR)) {
                        "$startDateString - ${endDateString.substring(5)}"
                    } else {
                        "$startDateString - $endDateString"
                    }
                }
                startDateString.isNotEmpty() && endDateString.isEmpty() -> {
                    startDateString
                }
                else -> {
                    ""
                }
            }
            Timber.e("haha ${displayString}")
            _selectButton.emit(displayString)
        }
    }
    fun settingFormatDate()
    {
        val startDateFormatted = startDate.value?.let { getFormatPeriodDay(it) } ?: ""
        val endDateFormatted = endDate.value?.let { getFormatPeriodDay(it) } ?: startDateFormatted

        _startDateFormat.value = startDateFormatted
        _endDateFormat.value = endDateFormatted
    }
}