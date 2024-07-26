package com.aos.floney.view.mypage

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
import com.aos.model.user.MyBooks
import com.aos.usecase.mypage.RecentBookkeySaveUseCase
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val mypageSearchUseCase: MypageSearchUseCase,
    private val recentBookKeySaveUseCase: RecentBookkeySaveUseCase
): BaseViewModel() {

    // 회원 정보
    private var _mypageInfo = MutableLiveData<UiMypageSearchModel>()
    val mypageInfo: LiveData<UiMypageSearchModel> get() = _mypageInfo

    // 가계부 리스트
    private var _mypageList = MutableLiveData<List<MyBooks>>()
    val mypageList: LiveData<List<MyBooks>> get() = _mypageList

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

    // 내역추가
    private var _clickedAddHistory = MutableEventFlow<String>()
    val clickedAddHistory: EventFlow<String> get() = _clickedAddHistory


    // 탭바로 추가할 경우
    fun onClickTabAddHistory() {
        viewModelScope.launch {
            _clickedAddHistory.emit(setTodayDate())
        }
    }

    // 오늘 날짜로 calendar 설정하기
    private fun setTodayDate(): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return date
    }

}