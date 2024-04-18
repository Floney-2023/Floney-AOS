package com.aos.floney.view.book.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
import com.aos.model.book.UiBookSettingModel
import com.aos.model.user.MyBooks
import com.aos.usecase.booksetting.BooksSettingGetUseCase
import com.aos.usecase.mypage.RecentBookkeySaveUseCase
import timber.log.Timber

@HiltViewModel
class BookSettingMainViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val booksSettingGetUseCase : BooksSettingGetUseCase
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

    // 회원 정보 페이지
    private var _informPage = MutableEventFlow<Boolean>()
    val informPage: EventFlow<Boolean> get() = _informPage

    // 설정 페이지
    private var _settingPage = MutableEventFlow<Boolean>()
    val settingPage: EventFlow<Boolean> get() = _settingPage

    // 가계부 추가 BottomSheet
    // 설정 페이지
    private var _bookAddBottomSheet = MutableEventFlow<Boolean>()
    val bookAddBottomSheet: EventFlow<Boolean> get() = _bookAddBottomSheet


    // 회원 정보 페이지
    private var _currencyPage = MutableEventFlow<Boolean>()
    val currencyPage: EventFlow<Boolean> get() = _currencyPage

    init{
        searchBookSettingItems()
    }
    // 마이페이지 정보 읽어오기
    fun searchBookSettingItems()
    {
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            booksSettingGetUseCase(prefs.getString("bookKey","")).onSuccess {

                // me가 true인 항목이 맨 앞에 오도록 정렬
                val sortedList = it.ourBookUsers.sortedByDescending { it.me }
                _bookSettingInfo.postValue(it.copy(ourBookUsers = sortedList))

                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
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

    }

    // 반복내역 설정
    fun onClickRepeat()
    {

    }
    // 즐겨찾기 설정
    fun onClickFavorite()
    {

    }
    // 가계부 초기화하기
    fun onClickBookInit()
    {

    }
    // 예산 설정
    fun onClickAssetSetting()
    {

    }
    // 초기 자산 설정
    fun onClickSTartMoneySetting()
    {

    }

    // 분류항목 관리
    fun onClickSettingCategory()
    {

    }

    // 이용 약관 페이지 이동
    fun onClickUsageRightPage()
    {

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

    }
    // 친구 추가
    fun onClickInviteFriend()
    {

    }

}