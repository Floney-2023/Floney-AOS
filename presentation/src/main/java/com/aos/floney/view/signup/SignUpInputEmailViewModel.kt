package com.aos.floney.view.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.usecase.SendEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpInputEmailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val sendEmailUseCase: SendEmailUseCase
): BaseViewModel() {

    var marketing: LiveData<Boolean> = stateHandle.getLiveData("marketing")
    private var _nextPage = MutableLiveData<Boolean>()
    val nextPage: LiveData<Boolean> get() = _nextPage

    // 이메일
    var email = MutableLiveData<String>("")

    // 이메일 전송
    fun onClickSendEmail() {
        if(email.value!!.isNotEmpty()) {
            if(isEmailValid()) {
                // 이메일 전송
                viewModelScope.launch(Dispatchers.IO) {
                    baseEvent(Event.ShowLoading)

                    sendEmailUseCase(email.value!!).onSuccess {
                        baseEvent(Event.HideLoading)
                        // 전송 성공
                        _nextPage.postValue(true)
                    }.onFailure {
                        baseEvent(Event.HideLoading)
                        baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                    }
                }
            } else {
                // 이메일이 유효하지 않은 형태일 경우
                baseEvent(Event.ShowToastRes(R.string.sign_up_request_valid_email))
            }
        } else {
            // 이메일이 비어 있을 경우
            baseEvent(Event.ShowToastRes(R.string.sign_up_request_email))
        }
    }

    // 이메일 유효성 체크
    private fun isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()
    }
}