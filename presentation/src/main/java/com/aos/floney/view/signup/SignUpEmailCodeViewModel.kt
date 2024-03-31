package com.aos.floney.view.signup

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.CheckEmailCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class SignUpEmailCodeViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val checkEmailCodeUseCase: CheckEmailCodeUseCase
) : BaseViewModel() {

    var email: LiveData<String> = stateHandle.getLiveData("email")
    var marketing: LiveData<Boolean> = stateHandle.getLiveData("marketing")


    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 다음 페이지 이동
    private var _nextPage = MutableEventFlow<Boolean>()
    val nextPage: EventFlow<Boolean> get() = _nextPage

    // 첫번째 코드
    var codeFirst = MutableLiveData<String>()

    // 두번째 코드
    var codeSecond = MutableLiveData<String>()

    // 세번째 코드
    var codeThird = MutableLiveData<String>()

    // 네번째 코드
    var codeFour = MutableLiveData<String>()

    // 다섯번째 코드
    var codeFifth = MutableLiveData<String>()

    // 여섯번째 코드
    var codeSix = MutableLiveData<String>()

    // 타이머 카운트 다운
    private var _timerText = MutableLiveData<String>()
    val timerText: LiveData<String> get() = _timerText

    // 타이머 만료 여부
    private var timerExpired = false

    init {
        startTimer()
    }

    // 5분 타이머 시작
    private fun startTimer() {
        val countDownTimer = object : CountDownTimer(300000, 1000) { // 5분(300000밀리초) 타이머
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                _timerText.value = "${minutes}:${
                    if (seconds < 10) {
                        "0$seconds"
                    } else {
                        seconds
                    }
                }"
            }

            override fun onFinish() {
                timerExpired = true // 타이머 만료 상태로 변경
                baseEvent(Event.ShowToastRes(R.string.sign_up_expired_code))
            }
        }
        countDownTimer.start()
    }

    // 이메일 코드 일치한지 체크
    fun checkEmailCode() {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkAllInput()) {
                if (!timerExpired) {
                    val email = email.value ?: ""
                    val code =
                        "${codeFirst.value}${codeSecond.value}${codeThird.value}${codeFour.value}${codeFifth.value}${codeSix.value}"
                    baseEvent(Event.ShowLoading)

                    checkEmailCodeUseCase(email, code).onSuccess {
                        baseEvent(Event.HideLoading)

                        _nextPage.emit(true)
                    }.onFailure {
                        baseEvent(Event.HideLoading)
                        baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                    }
                } else {
                    // 5분 타이머가 만료되었을 경우
                    baseEvent(Event.ShowToastRes(R.string.sign_up_expired_code))
                }
            } else {
                // 코드가 모두 입력되지 않았을 경우
                baseEvent(Event.ShowToastRes(R.string.sign_up_request_input_code))
            }
        }
    }

    // 이전 페이지로 이동
    fun onClickPreviousPage() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 전부 입력 되었는지 확인
    private fun checkAllInput(): Boolean {
        return codeFirst.value != "" && codeSecond.value != "" && codeThird.value != "" && codeFour.value != "" && codeFifth.value != "" && codeSix.value != ""
    }
}