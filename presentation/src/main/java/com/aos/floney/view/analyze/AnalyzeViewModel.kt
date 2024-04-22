package com.aos.floney.view.analyze

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnalyzeViewModel @Inject constructor(): BaseViewModel() {

    // 지출, 수입, 예산, 자산
    private var _flow = MutableLiveData<String>("지출")
    val flow: LiveData<String> get() = _flow


    private var _onClickSetBudget = MutableLiveData<Boolean>()
    val onClickSetBudget : LiveData<Boolean> get() = _onClickSetBudget

    // 지출, 수입, 이체 클릭
    fun onClickFlow(type: String) {
        _flow.postValue(type)
    }

    fun onClickSetBudget(flag: Boolean) {
        _onClickSetBudget.postValue(flag)
    }
}