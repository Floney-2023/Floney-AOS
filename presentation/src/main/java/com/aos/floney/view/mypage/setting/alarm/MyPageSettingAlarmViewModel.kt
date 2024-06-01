package com.aos.floney.view.mypage.setting.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.mypage.MarketingChangeUseCase
import com.aos.usecase.mypage.MarketingCheckUseCase
import com.aos.usecase.mypage.NicknameChangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel

class MyPageSettingAlarmViewModel @Inject constructor(
    private val marketingChangeUseCase : MarketingChangeUseCase,
    private val marketingCheckUseCase : MarketingCheckUseCase,
): BaseViewModel() {

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 마케팅 정보 수신
    private var _marketingTerms = MutableLiveData<Boolean>()
    val marketingTerms: LiveData<Boolean> get() = _marketingTerms

    init {
        checkMarketingTerms()
    }
    // 유저 마케팅 여부 수신 동의 가져오기
    fun checkMarketingTerms()
    {
        viewModelScope.launch(Dispatchers.IO) {
            marketingCheckUseCase().onSuccess {
                _marketingTerms.postValue(it.agree)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@MyPageSettingAlarmViewModel)))
            }
        }
    }
    // 이전 페이지로 이동
    fun onClickPreviousPage()
    {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
    // 마케팅 동의 변경 토글 버튼 클릭
    fun onClickMarketingTerms()
    {
        viewModelScope.launch(Dispatchers.IO) {
            marketingChangeUseCase(agree = !(marketingTerms.value)!!).onSuccess {
                _marketingTerms.postValue(!_marketingTerms.value!!)
                // 유저 마케팅 수신 동의 여부 변경
                baseEvent(Event.ShowSuccessToastRes(R.string.mypage_main_setting_marketing_response))
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@MyPageSettingAlarmViewModel)))
            }
        }
    }
}