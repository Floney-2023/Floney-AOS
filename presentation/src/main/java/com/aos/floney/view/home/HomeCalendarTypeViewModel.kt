package com.aos.floney.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.model.home.ExtData
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
    private val prefs: SharedPreferenceUtil,
    private val searchBookMonthUseCase: SearchBookMonthUseCase
) : BaseViewModel() {

    private var _getCalendarList = MutableLiveData<List<ListData>>()
    val getCalendarList: LiveData<List<ListData>> get() = _getCalendarList

    private var _getExtData = MutableLiveData<ExtData>()
    val getExtData: LiveData<ExtData> get() = _getExtData

    fun getBookMonth(_date: String = "") {
        val date = if(_date != "") {
            _date
        } else {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val firstDayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            firstDayFormat.format(calendar.time)
        }

        Timber.e("prefs.getString(\"bookKey\", \"\") ${prefs.getString("bookKey", "")}")

        viewModelScope.launch {
            baseEvent(Event.ShowLoading)
            searchBookMonthUseCase(bookKey = prefs.getString("bookKey", ""), date = date).onSuccess {
                 baseEvent(Event.HideLoading)
                _getCalendarList.postValue(it.data)
                _getExtData.postValue(it.extData)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

}