package com.aos.floney.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.CurrencyUtil
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.floney.util.getAdvertiseCheck
import com.aos.floney.util.getAdvertiseTenMinutesCheck
import com.aos.floney.util.getCurrentDateTimeString
import com.aos.model.book.getCurrencySymbolByCode
import com.aos.model.home.DayMoney
import com.aos.model.home.MonthMoney
import com.aos.model.home.UiBookDayModel
import com.aos.model.home.UiBookInfoModel
import com.aos.model.user.UserModel.userNickname
import com.aos.usecase.booksetting.BooksCurrencySearchUseCase
import com.aos.usecase.home.GetBookInfoUseCase
import com.aos.usecase.home.GetMoneyHistoryDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val getMoneyHistoryDaysUseCase: GetMoneyHistoryDaysUseCase,
    private val booksCurrencySearchUseCase : BooksCurrencySearchUseCase,
    private val getBookInfoUseCase: GetBookInfoUseCase,
) : BaseViewModel() {

    // 날짜 데이터
    private val _calendar = MutableStateFlow<Calendar>(Calendar.getInstance())

    // 표시 중인 날짜
    private var _showDate = MutableLiveData<String>()
    val showDate: LiveData<String> get() = _showDate

    private var _bookInfo = MutableLiveData<UiBookInfoModel>()
    val bookInfo: LiveData<UiBookInfoModel> get() = _bookInfo

    // 날짜 선택 버튼 클릭
    private var _clickedChoiceDate = MutableEventFlow<String>()
    val clickedChoiceDate: EventFlow<String> get() = _clickedChoiceDate

    // 이전 월 이동
    private var _clickedPreviousMonth = MutableEventFlow<String>()
    val clickedPreviousMonth: EventFlow<String> get() = _clickedPreviousMonth

    // 다음 월 이동
    private var _clickedNextMonth = MutableEventFlow<String>()
    val clickedNextMonth: EventFlow<String> get() = _clickedNextMonth

    // 캘린더, 일별 표시 타입
    private var _clickedShowType = MutableLiveData<String>("month")
    val clickedShowType: LiveData<String> get() = _clickedShowType

    // 내역추가
    private var _clickedAddHistory = MutableEventFlow<Boolean>()
    val clickedAddHistory: EventFlow<Boolean> get() = _clickedAddHistory

    private var _getMoneyDayData = MutableEventFlow<UiBookDayModel>()
    val getMoneyDayData: EventFlow<UiBookDayModel> get() = _getMoneyDayData

    private var _getMoneyDayList = MutableLiveData<List<DayMoney>>()
    val getMoneyDayList: LiveData<List<DayMoney>> get() = _getMoneyDayList

    private var _onClickedShowDetail = MutableLiveData<MonthMoney?>(null)
    val onClickedShowDetail: LiveData<MonthMoney?> get() = _onClickedShowDetail

    private lateinit var myNickname: String

    // 설정 페이지
    private var _settingPage = MutableEventFlow<Boolean>()
    val settingPage: EventFlow<Boolean> get() = _settingPage

    // 광고 ON/OFF
    private var _showAdvertisement = MutableLiveData<Boolean>()
    val showAdvertisement: LiveData<Boolean> get() = _showAdvertisement


    // 접근 권한 확인 O/X
    private var _accessCheck = MutableEventFlow<Boolean>()
    val accessCheck: EventFlow<Boolean> get() = _accessCheck


    init {
        getFormatDateMonth()
        setAdvertisement()
    }

    fun initCalendarMonth() {
//        _calendar.value = Calendar.getInstance()
        _showDate.value = getFormatDateMonth()
    }

    fun initCalendarDay() {
//        _calendar.value = Calendar.getInstance()
        _showDate.value = getFormatDateDay()
    }

    // 가계부 정보 가져오기
    fun getBookInfo(code: String) {
        viewModelScope.launch {
            baseEvent(Event.ShowLoading)
            getBookInfoUseCase(code).onSuccess {

                // 프로필 보기 여부 저장
                prefs.setBoolean("seeProfileStatus", it.seeProfileStatus)

                // 내 닉네임 저장
                it.ourBookUsers.forEach {
                    if (it.me) {
                        myNickname = it.name
                        userNickname = it.name
                    }
                }
                _bookInfo.postValue(it)

                // 화폐 단위 가져오기
                searchCurrency()
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@HomeViewModel)))
            }
        }
    }

    // 화폐 설정 조회
    fun searchCurrency(){
        viewModelScope.launch {
            booksCurrencySearchUseCase(prefs.getString("bookKey", "")).onSuccess {
                if(it.myBookCurrency != "") {
                    baseEvent(Event.HideLoading)
                    // 화폐 단위 저장
                    prefs.setString("symbol", getCurrencySymbolByCode(it.myBookCurrency))
                    CurrencyUtil.currency = getCurrencySymbolByCode(it.myBookCurrency)

                } else {
                    baseEvent(Event.HideLoading)
                    baseEvent(Event.ShowToastRes(R.string.currency_error))
                }
            }.onFailure {
                _accessCheck.emit(true)
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@HomeViewModel)))
            }
        }
    }

    // 유저 가계부 유효 확인
    fun getBookDays(date: String) {
        viewModelScope.launch {
            getMoneyHistoryDaysUseCase(prefs.getString("bookKey", ""), date).onSuccess {
                _getMoneyDayData.emit(it)
                _getMoneyDayList.postValue(it.data)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@HomeViewModel)))

            }
        }
    }

    // 이전 월 클릭
    fun onClickPreviousMonth() {
        viewModelScope.launch {
            if (_clickedShowType.value == "month") {
                updateCalendarMonth(-1)
                _clickedPreviousMonth.emit(getFormatDateMonth())
            } else {
                updateCalendarDay(-1)
                _clickedPreviousMonth.emit(getFormatDateDay())
            }
        }
    }

    // 다음 월 클릭
    fun onClickNextMonth() {
        viewModelScope.launch {
            if (_clickedShowType.value == "month") {
                updateCalendarMonth(1)
                _clickedNextMonth.emit(getFormatDateMonth())
            } else {
                updateCalendarDay(1)
                _clickedNextMonth.emit(getFormatDateDay())
            }
        }
    }

    // 다음 월 클릭
    fun onClickChoiceDate() {
        if (_clickedShowType.value == "month") {
            viewModelScope.launch {
                _clickedChoiceDate.emit(getFormatYearMonthDate())
            }
        }
    }

    // 일자 상세 표시 열기
    fun onClickShowDetail(item: MonthMoney) {
        updateCalendarClickedItem(item.year.toInt(), item.month.toInt(), item.day.toInt())
        _onClickedShowDetail.postValue(item)

        getBookDays(
            "${item.year}-${
                if (item.month.toInt() < 10) {
                    "0${item.month}"
                } else {
                    item.month
                }
            }-${
                if (item.day.toInt() < 10) {
                    "0${item.day}"
                } else {
                    item.day
                }
            }"
        )
    }

    // 일자 상세 표시 닫기
    fun onClickCloseShowDetail() {
        _onClickedShowDetail.postValue(null)
    }

    // 캘린더, 일별 표시타입 변경
    fun onClickShowType(type: String) {
        if (_clickedShowType.value != type) {
            _clickedShowType.value = type
        }
    }

    // 캘린더, 일별 표시타입 변경
    fun onClickAddHistory() {
        viewModelScope.launch {
            _clickedAddHistory.emit(true)
        }
    }

    // 탭바로 추가할 경우
    fun onClickTabAddHistory() {
        setTodayDate()

        viewModelScope.launch {
            _clickedAddHistory.emit(true)
        }
    }

    // 캘린더 값 변경
    private fun updateCalendarMonth(value: Int) {
        _calendar.value.set(Calendar.DAY_OF_MONTH, 1)
        _calendar.value.add(Calendar.MONTH, value)
    }

    // 캘린더 값 변경
    private fun updateCalendarDay(value: Int) {
        _calendar.value.add(Calendar.DATE, value)
    }

    // 캘린더 값 변경
    fun updateCalendarClickedItem(year: Int, month: Int, date: Int) {
        _calendar.value.set(Calendar.YEAR, year)
        _calendar.value.set(Calendar.MONTH, month - 1)
        _calendar.value.set(Calendar.DATE, date)

        viewModelScope.launch {
            if (_clickedShowType.value == "month") {
                _clickedNextMonth.emit(getFormatDateMonth())
            } else {
                _clickedNextMonth.emit(getFormatDateDay())
            }
        }
    }

    // 오늘 날짜로 calendar 설정하기
    private fun setTodayDate() {
        val dateArr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).split("-")
        _calendar.value.set(Calendar.YEAR, dateArr[0].toInt())
        _calendar.value.set(Calendar.MONTH, dateArr[1].toInt())
        _calendar.value.set(Calendar.DATE, dateArr[2].toInt())
    }

    // 날짜 포멧 결과 가져오기
    fun getFormatDateMonth(): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_calendar.value.time)
        val showDate = date.substring(0, 7).replace("-", ".")
        _showDate.postValue(showDate)
        return date
    }

    // 날짜 포멧 결과 가져오기
    fun getFormatDateDay(): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_calendar.value.time)
        val showDate = date.substring(5, 10).replace("-", ".")
        _showDate.postValue(showDate)
        return date
    }

    // 년, 월 일 가져오기
    private fun getFormatYearMonthDate(): String {
        return SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(_calendar.value.time) + "-01"
    }

    // 선택된 날짜 가져오기
    fun getClickDate(): String {
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

    // 내 닉네임 가져오기
    fun getMyNickname(): String {
        return myNickname
    }

    // 가계부 설정 페이지 이동
    fun onClickSettingPage() {
        viewModelScope.launch {
            val advertiseTime = prefs.getString("advertiseTime", "")
            val advertiseTenMinutes = prefs.getString("advertiseBookSettingTenMinutes", "")
            val showSettingPage = advertiseTime.isNotEmpty() || getAdvertiseTenMinutesCheck(advertiseTenMinutes) > 0

            if (getAdvertiseTenMinutesCheck(advertiseTenMinutes) <= 0) {
                prefs.setString("advertiseBookSettingTenMinutes", "")
            }

            _settingPage.emit(!showSettingPage)
        }
    }
    // 10분 광고 시간 기록
    fun updateAdvertiseTenMinutes(){
        prefs.setString("advertiseBookSettingTenMinutes", getCurrentDateTimeString())
    }
    // 광고 표시 여부
    fun setAdvertisement() {
        val advertiseTime = prefs.getString("advertiseTime", "")

        if (getAdvertiseCheck(advertiseTime) <= 0) {
            prefs.setString("advertiseTime", "")
        }

        _showAdvertisement.postValue(advertiseTime.isEmpty())
    }
    // 가계부 접근 확인
    fun setAccessCheck(booleanExtra: Boolean) {
        viewModelScope.launch {
            if(booleanExtra)
                _accessCheck.emit(true)
        }
    }
}