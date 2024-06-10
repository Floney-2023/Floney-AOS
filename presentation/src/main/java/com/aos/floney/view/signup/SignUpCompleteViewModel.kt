package com.aos.floney.view.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.mypage.MypageSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpCompleteViewModel @Inject constructor(
    private val mypageSearchUseCase : MypageSearchUseCase,
): BaseViewModel() {

    // 다음 페이지 이동
    private var _nextPage = MutableEventFlow<Boolean>()
    val nextPage: EventFlow<Boolean> get() = _nextPage

    // 닉네임
    var nickname = MutableLiveData<String>("")

    init{
        settingNickname()
    }
    fun settingNickname()
    {
        viewModelScope.launch(Dispatchers.IO) {
            mypageSearchUseCase().onSuccess {
                nickname.postValue("환영합니다\n${it.nickname}님!")
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@SignUpCompleteViewModel)))
            }
        }
    }
    // 플로니 시작하기 클릭
    fun onClickStartFloney() {
        viewModelScope.launch {
            _nextPage.emit(true)
        }
    }
}