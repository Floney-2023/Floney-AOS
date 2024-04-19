package com.aos.floney.view.analyze

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aos.floney.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyzeViewModel @Inject constructor(): BaseViewModel() {

    // 지출, 수입, 예산, 자산
    private var _flow = MutableLiveData<String>("지출")
    val flow: LiveData<String> get() = _flow


    // 지출, 수입, 이체 클릭
    fun onClickFlow(type: String) {
        _flow.postValue(type)
    }
}