package com.aos.floney.view.web

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.user.SocialUserModel
import com.aos.usecase.home.CheckUserBookUseCase
import com.aos.usecase.login.AuthTokenCheckUseCase
import com.aos.usecase.login.LoginUseCase
import com.aos.usecase.login.SocialLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
): BaseViewModel() {

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 뒤로 가기
    fun onClickPreviousPage() {
        viewModelScope.launch {
            baseEvent(Event.ShowLoading)
            _back.emit(true)
        }
    }

}