package com.aos.floney.view.onboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aos.floney.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardStartViewModel @Inject constructor(): BaseViewModel() {

    private var _onClickStartBtn = MutableLiveData<Boolean>()
    val onClickStartBtn: LiveData<Boolean> get() = _onClickStartBtn

    fun onClickStartBtn() {
        _onClickStartBtn.value = true
    }

}