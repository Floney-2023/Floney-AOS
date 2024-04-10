package com.aos.floney.view.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(): BaseViewModel() {

    private var _showCalendar = MutableEventFlow<Boolean>()
    val showCalendar: EventFlow<Boolean> get() =  _showCalendar

    fun onClickShowCalendar() {
        viewModelScope.launch {
            _showCalendar.emit(true)
        }
    }

}