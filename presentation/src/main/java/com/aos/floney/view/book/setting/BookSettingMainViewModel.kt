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
    init{
        searchBookSettingItems()
    }
    // 마이페이지 정보 읽어오기
    fun searchBookSettingItems()
    {
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            booksSettingGetUseCase(prefs.getString("bookKey","")).onSuccess {
                _bookSettingInfo.postValue(it)
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

    // 문의 하기 페이지 이동
    fun onClickAnswerPage()
    {

    }

    // 공지 사항 페이지 이동
    fun onClickNoticePage()
    {

    }

    // 리뷰 작성하기 페이지 이동
    fun onClickReviewPage()
    {

    }

    // 개인 정보 처리방침 페이지 이동
    fun onClickPrivateRolePage()
    {

    }

    // 이용 약관 페이지 이동
    fun onClickUsageRightPage()
    {

    }
    // 가계부 추가
    fun onClickBookAdd()
    {
        viewModelScope.launch {
            _bookAddBottomSheet.emit(true)
        }
    }

}