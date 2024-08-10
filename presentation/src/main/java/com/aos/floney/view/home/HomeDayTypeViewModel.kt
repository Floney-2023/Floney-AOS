package com.aos.floney.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.home.DayMoney
import com.aos.model.home.ExtData
import com.aos.model.home.MonthMoney
import com.aos.model.home.UiBookDayModel
import com.aos.usecase.home.GetMoneyHistoryDaysUseCase
import com.aos.usecase.home.GetMoneyHistoryMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeDayTypeViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil
): BaseViewModel() {

    private var _getDayList = MutableLiveData<List<DayMoney>>()
    val getDayList: LiveData<List<DayMoney>> get() = _getDayList

    private var _getExtData = MutableLiveData<ExtData>()
    val getExtData: LiveData<ExtData> get() = _getExtData

    private var _clickAddHistory = MutableEventFlow<Boolean>()
    val clickAddHistory: EventFlow<Boolean> get() = _clickAddHistory

    fun updateMoneyDay(item: UiBookDayModel) {
        Timber.e("item ${item.data}")

        val updatedData = item.data.map { dayMoney ->
            dayMoney.copy(seeProfileStatus = prefs.getBoolean("seeProfileStatus", false))
        }

        val sortedData = updatedData.sortedWith(compareBy(
            { it.id == -1 },
            { it.repeatDuration == "없음" }
        ))
        _getDayList.value = sortedData
        _getExtData.value = item.extData
    }

    fun onClickAddHistoryBtn() {
        viewModelScope.launch {
            _clickAddHistory.emit(true)
        }
    }
}