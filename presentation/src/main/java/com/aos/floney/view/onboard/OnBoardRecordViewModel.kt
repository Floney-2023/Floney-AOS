package com.aos.floney.view.onboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aos.floney.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OnBoardRecordViewModel @Inject constructor(): BaseViewModel() {

    private var _onClickSkipBtn = MutableLiveData<Boolean>()
    val onClickSkipBtn: LiveData<Boolean> get() = _onClickSkipBtn

    fun onClickedSkipBtn() {
        _onClickSkipBtn.value = true
    }


}