package com.aos.floney.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.model.home.ListData
import com.aos.model.home.UiBookMonthModel
import com.aos.usecase.home.SearchBookMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeCalendarTypeViewModel @Inject constructor(
    private val searchBookMonthUseCase: SearchBookMonthUseCase
) : BaseViewModel() {

    // 날짜 데이터
    private val _calendar = MutableStateFlow<Calendar>(Calendar.getInstance())
    val calendar: MutableStateFlow<Calendar> get() = _calendar

    private var _getCalendarList = MutableLiveData<List<ListData>>()
    val getCalendarList: LiveData<List<ListData>> get() = _getCalendarList

    init {
        _calendar.value = Calendar.getInstance()
    }

    fun getBookMonth() {
        val firstDayOfMonth = _calendar.value.clone() as Calendar
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        viewModelScope.launch {
            searchBookMonthUseCase(bookKey = "BB653EEE", date = firstDayFormat.format(firstDayOfMonth.time)).onSuccess {
                Timber.e("searchBookMonthUseCase $it")
                _getCalendarList.postValue(it.data)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

}