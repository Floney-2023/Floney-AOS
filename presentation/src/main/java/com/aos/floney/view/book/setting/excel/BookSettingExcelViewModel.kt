package com.aos.floney.view.book.setting.excel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.formatNumber
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.UiBookCategory
import com.aos.model.home.DayMoneyModifyItem
import com.aos.usecase.booksetting.BooksCategoryAddUseCase
import com.aos.usecase.booksetting.BooksCategoryDeleteUseCase
import com.aos.usecase.history.GetBookCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BookSettingExcelViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val booksCategoryAddUseCase: BooksCategoryAddUseCase
) : BaseViewModel() {

    // 이전 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back


    // 이전 페이지
    private var _completePage = MutableEventFlow<String>()
    val completePage: EventFlow<String> get() = _completePage

    // 자산, 지출, 수입, 이체
    var flow = MutableLiveData<String>("이번달")

    fun onClickedExit() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 엑셀 내보내기
    fun onClickAddComplete() {
        if (flow.value!="") {
            viewModelScope.launch(Dispatchers.IO) {
                booksCategoryAddUseCase(
                    bookKey = prefs.getString("bookKey", ""),
                    getExcelDuration(flow.value!!),
                    getCurrentDate(flow.value!!)
                ).onSuccess {
                    _completePage.emit(it.name)
                }.onFailure {
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                }
            }
        } else{
            baseEvent(Event.ShowToast("항목 이름을 입력해주세요."))
        }

    }

    // 이번달, 저번달, 올해, 작년, 전체 클릭
    fun onClickFlow(type: String) {
        flow.postValue(type)
    }
    private fun getCurrentDate(category: String): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return when (category) {
            "이번달" -> {
                currentDate.withDayOfMonth(1).format(formatter) // 이번 달 1일
            }
            "저번달" -> {
                currentDate.minusMonths(1).withDayOfMonth(1).format(formatter) // 저번 달 1일
            }
            "작년" -> {
                currentDate.minusYears(1).withDayOfYear(1).format(formatter) // 작년 1월 1일
            }
            "올해" -> {
                currentDate.withDayOfYear(1).format(formatter) // 올해 1월 1일
            }
            "전체" -> {
                currentDate.withDayOfMonth(1).format(formatter) // 이번 달 1일
            }
            else -> ""
        }
    }
    private fun getExcelDuration(category: String): String {
        return when (category) {
            "이번달" -> {
                "THIS_MONTH"
            }

            "저번달" -> {
                "LAST_MONTH"
            }

            "작년" -> {
                "LAST_YEAR"
            }

            "올해" -> {
                "ONE_YEAR"
            }

            "전체" -> {
                "ALL"
            }

            else -> ""
        }
    }
}