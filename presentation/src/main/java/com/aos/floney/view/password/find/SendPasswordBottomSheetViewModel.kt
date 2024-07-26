package com.aos.floney.view.password.find

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.password.SendTempPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendPasswordBottomSheetViewModel @Inject constructor(): BaseViewModel() {

    var _onClickedLoginBtn = MutableEventFlow<Boolean>()
    val onClickedLoginBtn: EventFlow<Boolean> get() = _onClickedLoginBtn

    fun onClickedLoginBtn() {
        viewModelScope.launch {
            _onClickedLoginBtn.emit(true)
        }
    }

}