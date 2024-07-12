package com.aos.floney.view.book.setting

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.aos.floney.util.getCurrentDateTimeString
import com.aos.model.book.UiBookSettingModel
import com.aos.model.user.MyBooks
import com.aos.usecase.booksetting.BooksInitUseCase
import com.aos.usecase.booksetting.BooksOutUseCase
import com.aos.usecase.booksetting.BooksSettingGetUseCase
import com.aos.usecase.home.CheckUserBookUseCase
import com.aos.usecase.mypage.AlarmSaveGetUseCase
import com.aos.usecase.mypage.RecentBookkeySaveUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltViewModel
class BookSettingMainViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val booksSettingGetUseCase : BooksSettingGetUseCase,
    private val booksInitUseCase : BooksInitUseCase,
    private val booksOutUseCase: BooksOutUseCase,
    private val checkUserBookUseCase: CheckUserBookUseCase,
    private val alarmSaveGetUseCase : AlarmSaveGetUseCase
): BaseViewModel() {

    // 회원 닉네임
    private var _bookSettingInfo = MutableLiveData<UiBookSettingModel>()
    val bookSettingInfo: LiveData<UiBookSettingModel> get() = _bookSettingInfo

    // 가계부 리스트
    private var _mypageList = MutableLiveData<List<MyBooks>>()
    val mypageList: LiveData<List<MyBooks>> get() = _mypageList


    // 이전 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 가계부 초기화 페이지
    private var _initPage = MutableEventFlow<Boolean>()
    val initPage: EventFlow<Boolean> get() = _initPage

    // 이월 설정 페이지
    private var _carryInfoPage = MutableEventFlow<Boolean>()
    val carryInfoPage: EventFlow<Boolean> get() = _carryInfoPage

    // 설정 페이지
    private var _settingPage = MutableEventFlow<Boolean>()
    val settingPage: EventFlow<Boolean> get() = _settingPage

    // 초기 자산 페이지
    private var _assetPage = MutableEventFlow<Boolean>()
    val assetPage: EventFlow<Boolean> get() = _assetPage


    // 화폐 정보 페이지
    private var _currencyPage = MutableEventFlow<Boolean>()
    val currencyPage: EventFlow<Boolean> get() = _currencyPage

    // 예산 페이지
    private var _budgetPage = MutableEventFlow<Boolean>()
    val budgetPage: EventFlow<Boolean> get() = _budgetPage

    // 카테고리 페이지
    private var _categoryPage = MutableEventFlow<Boolean>()
    val categoryPage: EventFlow<Boolean> get() = _categoryPage

    // 엑셀 내보내기 페이지
    private var _excelPage = MutableEventFlow<Boolean>()
    val excelPage: EventFlow<Boolean> get() = _excelPage

    // 친구 초대 페이지
    private var _invitePage = MutableEventFlow<Boolean>()
    val invitePage: EventFlow<Boolean> get() = _invitePage


    // 친구 초대 페이지
    private var _repeatPage = MutableEventFlow<Boolean>()
    val repeatPage: EventFlow<Boolean> get() = _repeatPage

    // 가계부 나가기 팝업
    private var _exitPopup = MutableEventFlow<Boolean>()
    val exitPopup: EventFlow<Boolean> get() = _exitPopup

    // 가계부 나가기 완료
    private var _exitPage = MutableEventFlow<Boolean>()
    val exitPage: EventFlow<Boolean> get() = _exitPage

    // 가계부 즐겨찾기 페이지
    private var _favoritePage = MutableEventFlow<Boolean>()
    val favoritePage: EventFlow<Boolean> get() = _favoritePage

    // 마이페이지 정보 읽어오기
    fun searchBookSettingItems()
    {
        viewModelScope.launch(Dispatchers.IO) {
            booksSettingGetUseCase(prefs.getString("bookKey","")).onSuccess {
                // me가 true인 항목이 맨 앞에 오도록 정렬
                val sortedList = it.ourBookUsers.sortedByDescending { it.me }

                _bookSettingInfo.postValue(it.copy(ourBookUsers = sortedList))
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookSettingMainViewModel)))
            }
        }
    }

    // 가계부 초기화하기
    fun initBook(){
        viewModelScope.launch {
            if(prefs.getString("bookKey","").isNotEmpty()) {
                baseEvent(Event.ShowLoading)
                booksInitUseCase(prefs.getString("bookKey","")).onSuccess {
                    delay(1)
                    baseEvent(Event.HideLoading)
                    baseEvent(Event.ShowSuccessToast("가계부가 초기화 되었습니다."))
                    alarmInitSave()
                }.onFailure {
                    baseEvent(Event.HideLoading)
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookSettingMainViewModel)))
                }
            }
        }
    }

    // 가계부 초기화 알람 저장
    fun alarmInitSave()
    {
        viewModelScope.launch {
            if(prefs.getString("bookKey","").isNotEmpty()) {
                baseEvent(Event.ShowLoading)

                bookSettingInfo.value?.ourBookUsers?.map {
                    alarmSaveGetUseCase(
                        prefs.getString("bookKey",""),
                        "플로니",
                        "${_bookSettingInfo.value!!.bookName} 가계부가 초기화 되었어요.",
                        "icon_noti_reset",
                        it.email,
                        getCurrentDateTimeString()
                    ).onSuccess {
                        baseEvent(Event.HideLoading)
                        Timber.e("save gg ")
                    }.onFailure {
                        baseEvent(Event.HideLoading)
                        baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                    }
                }
            }
        }
    }
    // 가계부 나가기 알람 저장
    fun alarmExitSave()
    {
        viewModelScope.launch {
            if(prefs.getString("bookKey","").isNotEmpty()) {
                baseEvent(Event.ShowLoading)
                bookSettingInfo.value?.ourBookUsers?.forEachIndexed { index, user ->
                    if (index != 0) { // 인덱스가 0이 아닌 경우에만 실행(본인 제외, 알람)
                        alarmSaveGetUseCase(
                            prefs.getString("bookKey",""),
                            "플로니",
                            "${_bookSettingInfo.value!!.ourBookUsers[index].name}님이 ${_bookSettingInfo.value!!.bookName} 가계부를 나갔습니다.",
                            "icon_noti_exit",
                            _bookSettingInfo.value!!.ourBookUsers[index].email,
                            getCurrentDateTimeString()
                        ).onSuccess {
                            baseEvent(Event.HideLoading)
                            settingBookKey()
                            Timber.e("save gg ")
                        }.onFailure {
                            baseEvent(Event.HideLoading)
                            baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                        }
                    }
                }
            }
        }
    }

    // 가계부 나가기
    fun onBookExit()
    {
        viewModelScope.launch(Dispatchers.IO) {
            booksOutUseCase(prefs.getString("bookKey","")).onSuccess {
                alarmExitSave()
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookSettingMainViewModel)))
            }
        }
    }

    // 이월 설정
    fun changeCarryOver(carryOver : Boolean){
        viewModelScope.launch {
            val currentInfo = bookSettingInfo.value
            _bookSettingInfo.postValue(currentInfo?.copy(carryOver = carryOver))
        }
    }
    // 이전 페이지 이동
    fun onClickPreviousPage()
    {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 가계부 상세 설정 페이지 이동
    fun onClickInformPage()
    {
        viewModelScope.launch {
            _settingPage.emit(true)
        }
    }

    // 이월설정
    fun onClickCarryInfoSetting()
    {
        viewModelScope.launch {
            _carryInfoPage.emit(true)
        }
    }

    // 반복내역 설정
    fun onClickRepeat()
    {
        viewModelScope.launch {
            _repeatPage.emit(true)
        }
    }
    // 즐겨찾기 설정
    fun onClickFavorite()
    {
        viewModelScope.launch {
            _favoritePage.emit(true)
        }
    }
    // 가계부 초기화하기
    fun onClickBookInit()
    {
        viewModelScope.launch {
            _initPage.emit(true)
        }
    }
    // 예산 설정
    fun onClickAssetSetting()
    {
        viewModelScope.launch {
            _budgetPage.emit(true)
        }
    }
    // 초기 자산 설정
    fun onClickSTartMoneySetting()
    {
        viewModelScope.launch {
            _assetPage.emit(true)
        }
    }

    // 분류항목 관리
    fun onClickSettingCategory()
    {
        viewModelScope.launch {
            _categoryPage.emit(true)
        }
    }

    // 화폐 설정
    fun onClickSettingMoney()
    {
        viewModelScope.launch {
            _currencyPage.emit(true)
        }
    }
    // 엑셀 내보내기
    fun onClickExcelExport()
    {
        viewModelScope.launch {
            _excelPage.emit(true)
        }
    }

    // 친구 추가
    fun onClickInviteFriend()
    {
        viewModelScope.launch {
            _invitePage.emit(true)
        }
    }

    // 가계부 나가기
    fun onClickBookExit()
    {
        viewModelScope.launch {
            _exitPopup.emit(true)
        }
    }

    fun settingBookKey(){
        viewModelScope.launch {
            checkUserBookUseCase().onSuccess {
                if(it.bookKey != "") {
                    prefs.setString("bookKey", it.bookKey)
                    _exitPage.emit(true)
                } else {
                    _exitPage.emit(false)
                }
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookSettingMainViewModel)))
            }
        }
    }
}