package com.aos.floney.view.mypage.main.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.CommonUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.user.UiMypageSearchModel
import com.aos.usecase.mypage.MypageSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.util.convertStringToDate
import com.aos.floney.util.getAdvertiseCheck
import com.aos.floney.util.getCurrentDateTimeString
import com.aos.model.user.MyBooks
import com.aos.usecase.mypage.RecentBookkeySaveUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.math.abs

@HiltViewModel
class MyPageMainViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val mypageSearchUseCase : MypageSearchUseCase,
    private val recentBookKeySaveUseCase : RecentBookkeySaveUseCase
): BaseViewModel() {

    // 광고 시간
    private var _advertiseTime = MutableLiveData<String>()
    val advertiseTime: LiveData<String> get() = _advertiseTime

    // 회원 정보
    private var _mypageInfo = MutableLiveData<UiMypageSearchModel>()
    val mypageInfo: LiveData<UiMypageSearchModel> get() = _mypageInfo

    // 가계부 리스트
    private var _mypageList = MutableLiveData<List<MyBooks>>()
    val mypageList: LiveData<List<MyBooks>> get() = _mypageList

    // 알람 페이지
    private var _alarmPage = MutableEventFlow<Boolean>()
    val alarmPage: EventFlow<Boolean> get() = _alarmPage

    // 메일 문의하기 페이지
    private var _mailPage = MutableEventFlow<Boolean>()
    val mailPage: EventFlow<Boolean> get() = _mailPage

    // 공지사항 페이지
    private var _noticePage = MutableEventFlow<Boolean>()
    val noticePage: EventFlow<Boolean> get() = _noticePage

    // 회원 정보 페이지
    private var _informPage = MutableEventFlow<Boolean>()
    val informPage: EventFlow<Boolean> get() = _informPage

    // 설정 페이지
    private var _settingPage = MutableEventFlow<Boolean>()
    val settingPage: EventFlow<Boolean> get() = _settingPage

    // 개인 정보 페이지
    private var _privatePage = MutableEventFlow<Boolean>()
    val privatePage: EventFlow<Boolean> get() = _privatePage
    // 이용 약관 페이지
    private var _usageRightPage = MutableEventFlow<Boolean>()
    val usageRightPage: EventFlow<Boolean> get() = _usageRightPage

    // 가계부 추가 BottomSheet
    private var _bookAddBottomSheet = MutableEventFlow<Boolean>()
    val bookAddBottomSheet: EventFlow<Boolean> get() = _bookAddBottomSheet

    // 광고 페이지
    private var _adMobPage = MutableEventFlow<Boolean>()
    val adMobPage: EventFlow<Boolean> get() = _adMobPage

    // 제안하기 페이지
    private var _supposePage = MutableEventFlow<Boolean>()
    val supposePage: EventFlow<Boolean> get() = _supposePage

    init{
        settingAdvertiseTime()
        searchMypageItems()
    }
    // 광고 남은 시간 설정
    fun settingAdvertiseTime(){
        val adverseTiseTime = prefs.getString("advertiseTime", "")
        if (adverseTiseTime.isNotEmpty()){
            val remainingMinutes = getAdvertiseCheck(adverseTiseTime)

            if (remainingMinutes <= 0) {
                prefs.setString("advertiseTime", "")
                _advertiseTime.postValue("06:00")
            } else {
                val hours = remainingMinutes / 60
                val minutes = remainingMinutes % 60
                _advertiseTime.postValue(String.format("%02d:%02d", hours, minutes))
            }

        }
        else {
            _advertiseTime.postValue("06:00")
        }
    }
    // 광고 시청 시간 설정
    fun updateAdvertiseTime(){
        prefs.setString("advertiseTime", getCurrentDateTimeString())
        settingAdvertiseTime()
    }
    // 마이페이지 정보 읽어오기
    fun searchMypageItems()
    {
        viewModelScope.launch(Dispatchers.IO) {
            mypageSearchUseCase().onSuccess {

                var sortedBooks= it.myBooks.sortedByDescending { it.bookKey == prefs.getString("bookKey","") }

                val updatedResult = it.copy(myBooks = sortedBooks.map { myBook ->
                    if (myBook.bookKey == prefs.getString("bookKey", "")) {
                        myBook.copy(recentCheck = true)
                    } else {
                        myBook.copy(recentCheck = false)
                    }
                })

                _mypageInfo.postValue(updatedResult)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }
    // 알람 페이지 이동
    fun onClickAlarmPage()
    {
        viewModelScope.launch {
            _alarmPage.emit(true)
        }
    }

    // 설정 페이지 이동
    fun onClickSettingPage()
    {
        viewModelScope.launch {
            _settingPage.emit(true)
        }
    }

    // 회원 정보 페이지 이동
    fun onClickInformPage()
    {
        viewModelScope.launch {
            _informPage.emit(true)
        }
    }

    // 메일 문의 하기 페이지 이동
    fun onClickAnswerPage()
    {
        viewModelScope.launch {
            _mailPage.emit(true)
        }
    }

    // 공지 사항 페이지 이동
    fun onClickNoticePage()
    {
        viewModelScope.launch {
            _noticePage.emit(true)
        }
    }

    // 리뷰 작성하기 페이지 이동
    fun onClickReviewPage()
    {

    }

    // 개인 정보 처리방침 페이지 이동
    fun onClickPrivateRolePage()
    {
        viewModelScope.launch {
            _privatePage.emit(true)
        }
    }

    // 이용 약관 페이지 이동
    fun onClickUsageRightPage()
    {
        viewModelScope.launch {
            _usageRightPage.emit(true)
        }
    }

    // 최근 저장 가계부 저장
    fun settingBookKey(bookKey: String){
        viewModelScope.launch(Dispatchers.Main) {

            // 애니메이션 사이클 지속 시간 계산
            val animationDelay = 200L
            val animationDuration = 600L
            val minimumCycleDuration = animationDuration + animationDelay * 2

            withContext(Dispatchers.IO) {
                recentBookKeySaveUseCase(bookKey).onSuccess {
                    prefs.setString("bookKey", bookKey)

                    if (mypageInfo.value!!.myBooks.size != 1) {// 가계부가 2개 이상일 때만 로딩 싸이클
                        baseEvent(Event.ShowLoading)

                        val sortedBooks =
                            _mypageInfo.value!!.myBooks.sortedByDescending { it.bookKey == bookKey }

                        val updatedResult =
                            _mypageInfo.value!!.copy(myBooks = sortedBooks.map { myBook ->
                                if (myBook.bookKey == bookKey) {
                                    myBook.copy(recentCheck = true)
                                } else {
                                    myBook.copy(recentCheck = false)
                                }
                            })

                        delay(minimumCycleDuration)
                        baseEvent(Event.HideLoading)

                        _mypageInfo.postValue(updatedResult)
                    }
                }.onFailure {
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                }
            }
        }
    }

    // 가계부 추가
    fun onClickBookAdd()
    {
        viewModelScope.launch {
            _bookAddBottomSheet.emit(true)
        }
    }

    // 광고 시청
    fun onClickAdMob()
    {
        viewModelScope.launch {
            _adMobPage.emit(true)
        }
    }

    // 유저 프로필 이미지 불러오기
    fun getUserProfile(): String {
        return CommonUtil.userProfileImg
    }

    // 카페 제안하기
    fun onClickSuppose(){
        viewModelScope.launch {
            _supposePage.emit(true)
        }
    }
}