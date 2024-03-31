package com.aos.floney.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpCompleteViewModel @Inject constructor(): BaseViewModel() {

    // 다음 페이지 이동
    private var _nextPage = MutableEventFlow<Boolean>()
    val nextPage: EventFlow<Boolean> get() = _nextPage

    // 플로니 시작하기 클릭
    fun onClickStartFloney() {
        viewModelScope.launch {
            _nextPage.emit(true)
        }
    }
}