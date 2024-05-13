package com.aos.floney.view.settleup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettleUpViewModel @Inject constructor(
): BaseViewModel() {


    private var _bottom = MutableLiveData<Boolean>(true)
    val bottom: LiveData<Boolean> get() = _bottom

    private var _bookKey = MutableLiveData<String>()
    val bookKey: LiveData<String> get() = _bookKey

    private var _id = MutableLiveData<Long>()
    val id: LiveData<Long> get() = _id

    private var _sharePage = MutableEventFlow<Boolean>()
    val sharePage: EventFlow<Boolean> get() = _sharePage


    fun settingBookKey(id: Long, bk: String){
        viewModelScope.launch {
            _bookKey.value = bk
            _id.value = id
            _sharePage.emit(true)
        }
    }

    fun bottomSee(check : Boolean){
        viewModelScope.launch {
            _bottom.postValue(check)
        }
    }
}