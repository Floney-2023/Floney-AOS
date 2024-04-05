package com.aos.floney.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.model.home.DayMoney
import com.aos.model.home.ExtData
import com.aos.model.home.MonthMoney
import com.aos.model.home.UiBookDayModel
import com.aos.usecase.home.GetMoneyHistoryDaysUseCase
import com.aos.usecase.home.GetMoneyHistoryMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeDayTypeViewModel @Inject constructor(): BaseViewModel() {

    private var _getDayList = MutableLiveData<List<DayMoney>>()
    val getDayList: LiveData<List<DayMoney>> get() = _getDayList

    private var _getExtData = MutableLiveData<ExtData>()
    val getExtData: LiveData<ExtData> get() = _getExtData

    fun updateMoneyDay(data: UiBookDayModel) {
        _getDayList.value = data.data
        _getExtData.value = data.extData
    }
}