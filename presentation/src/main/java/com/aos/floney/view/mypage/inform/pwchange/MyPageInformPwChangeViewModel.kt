package com.aos.floney.view.mypage.inform.pwchange

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorCode
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.mypage.PasswordChangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageInformPwChangeViewModel @Inject constructor(
    private val passwordChangeUseCase : PasswordChangeUseCase
): BaseViewModel() {

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 다음 페이지 이동
    private var _checkBtn = MutableEventFlow<Boolean>()
    val checkBtn: EventFlow<Boolean> get() = _checkBtn

    // 현재 비밀번호
    var nowPassword = MutableLiveData<String>("")

    // 새로운 비밀번호
    var newPassword = MutableLiveData<String>("")

    // 새로운 비밀번호 확인
    var newRePassword = MutableLiveData<String>("")

    // 이전 페이지로 이동
    fun onClickPreviousPage()
    {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 비밀번호 변경 버튼 클릭
    fun onClickPwChangeCheck()
    {
        if (newPassword.value != "") {
            if (newRePassword.value != "") {
                if (newPassword.value.equals(newRePassword.value)) {
                    if (isPasswordValid(newPassword.value ?: "")) {
                        if (nowPassword.value != "") {
                            // 비밀번호 변경 시도
                            viewModelScope.launch(Dispatchers.IO) {
                                baseEvent(Event.ShowLoading)
                                passwordChangeUseCase(newPassword = newPassword.value ?: "", oldPassword = nowPassword.value ?: "").onSuccess {
                                    // 비밀번호 변경 성공
                                    baseEvent(Event.ShowSuccessToast("비밀번호 변경이 완료되었습니다"))
                                    baseEvent(Event.HideLoading)
                                    _checkBtn.emit(true)
                                }.onFailure {
                                    baseEvent(Event.HideLoading)

                                    val errorCode = it.message.parseErrorCode()

                                    val message = when (errorCode) {
                                        "U008" -> "일치하는 회원이 없습니다."
                                        "U017" -> "이전에 사용한 비밀번호 입니다."
                                        "U002" -> "현재 비밀번호가 일치하지 않습니다."
                                        else -> "알 수 없는 오류입니다. 다시 시도해 주세요."
                                    }
                                    baseEvent(Event.ShowToast(message))
                                }
                            }
                        } else {
                            // 현재 비밀번호 비어있을 경우
                            baseEvent(Event.ShowToastRes(R.string.mypage_main_inform_pwchange_nowpw_request))
                        }
                    } else {
                        // 비밀번호 정규식 틀릴 경우
                        baseEvent(Event.ShowToastRes(R.string.mypage_main_inform_pwchange_check_password_rule))
                    }
                } else {
                    // 비밀번호 / 비밀번호 확인 일치하지 않을 경우
                    baseEvent(Event.ShowToastRes(R.string.mypage_main_inform_pwchange_not_match_password))
                }
            } else {
                // 새 비밀번호 확인 비어있을 경우
                baseEvent(Event.ShowToastRes(R.string.mypage_main_inform_pwchange_request_input_re_password))
            }
        } else {
            // 새 비밀번호 비어있을 경우
            baseEvent(Event.ShowToastRes(R.string.mypage_main_inform_pwchange_request_input_password))
        }
    }
    // 비밀번호 유효성 검사 영문 대소문자 구분 없음, 숫자, 특수문자 8자 이상
    private fun isPasswordValid(password: String): Boolean {
        val passwordRegex =
            Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,}\$")
        return passwordRegex.matches(password)
    }

}