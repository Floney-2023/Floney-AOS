package com.aos.floney.view.mypage

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
import com.aos.model.user.MyBooks
import com.aos.usecase.mypage.RecentBookkeySaveUseCase
import timber.log.Timber

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val mypageSearchUseCase : MypageSearchUseCase,
    private val recentBookKeySaveUseCase : RecentBookkeySaveUseCase
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
    init{
        searchMypageItems()
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
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@MyPageViewModel)))
            }
        }
    }
    // 알람 페이지 이동
    fun onClickAlarmPage()
    {

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
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            recentBookKeySaveUseCase(bookKey).onSuccess {
                prefs.setString("bookKey",bookKey)

                val sortedBooks= _mypageInfo.value!!.myBooks.sortedByDescending { it.bookKey == bookKey}

                val updatedResult = _mypageInfo.value!!.copy(myBooks = sortedBooks.map { myBook ->
                    if (myBook.bookKey == bookKey) {
                        myBook.copy(recentCheck = true)
                    } else {
                        myBook.copy(recentCheck = false)
                    }
                })

                _mypageInfo.postValue(updatedResult)
                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@MyPageViewModel)))
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

}