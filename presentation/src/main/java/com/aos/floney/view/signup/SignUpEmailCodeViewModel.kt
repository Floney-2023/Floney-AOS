package com.aos.floney.view.signup

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aos.floney.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpEmailCodeViewModel @Inject constructor(): BaseViewModel() {

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
    // 타이머 만료
    private var _timerExpired = MutableLiveData<Boolean>()
    val timerExpired: LiveData<Boolean> get() = _timerExpired

    init {
        startTimer()
    }

    // 5분 타이머 시작
    private fun startTimer() {
        val countDownTimer = object : CountDownTimer(300000, 1000) { // 5분(300000밀리초) 타이머
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timeString = String.format("%02d:%02d", minutes, seconds)
                _timerText.value = timeString
            }

            override fun onFinish() {
                // 타이머가 종료되면 필요한 작업을 수행하세요.
                _timerExpired.value = true // 타이머 만료 상태로 변경
            }
        }
        countDownTimer.start()
    }

    // 이메일 코드 일치한지 체크
    private fun checkEmailCode() {

    }

}