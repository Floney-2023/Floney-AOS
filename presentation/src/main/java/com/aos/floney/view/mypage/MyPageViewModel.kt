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

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val mypageSearchUseCase : MypageSearchUseCase
): BaseViewModel() {

    // 회원 닉네임
    private var _mypageInfo = MutableLiveData<UiMypageSearchModel>()
    val mypageInfo: LiveData<UiMypageSearchModel> get() = _mypageInfo

    // 회원 이메일
    private var _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    // 회원 정보 페이지
    private var _informPage = MutableEventFlow<Boolean>()
    val informPage: EventFlow<Boolean> get() = _informPage

    // 설정 페이지
    private var _settingPage = MutableEventFlow<Boolean>()
    val settingPage: EventFlow<Boolean> get() = _settingPage

    init{
        searchMypageItems()
    }
    // 마이페이지 정보 읽어오기
    fun searchMypageItems()
    {
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            mypageSearchUseCase().onSuccess {

                _mypageInfo.postValue(it)

                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
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
}