package com.aos.floney.view.settleup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.usecase.settlement.BooksUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettleUpPeriodSelectViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val booksUsersUseCase : BooksUsersUseCase
): BaseViewModel() {


    var memberArray: LiveData<Array<String>> = stateHandle.getLiveData("member")

    // 선택 날짜 String format
    private var _selectDay = MutableLiveData<String>("")
    val selectDay: LiveData<String> get() = _selectDay

    // 시작 날짜
    private var _startDay = MutableLiveData<String>("")
    val startDay: LiveData<String> get() = _startDay

    // 끝 날짜
    private var _endDay = MutableLiveData<String>("")
    val endDay: LiveData<String> get() = _endDay

    // 다음 정산 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 다음 정산 페이지
    private var _nextPage = MutableEventFlow<Boolean>()
    val nextPage: EventFlow<Boolean> get() = _nextPage

    // 처음 정산하기 페이지
    private var _settlementPage = MutableEventFlow<Boolean>()
    val settlementPage: EventFlow<Boolean> get() = _settlementPage

    // bottomSheet 불러오기
    // 처음 정산하기 페이지
    private var _periodBottomSheetPage = MutableEventFlow<Boolean>()
    val periodBottomSheetPage: EventFlow<Boolean> get() = _periodBottomSheetPage

    // bottomSheet 불러오기
    fun onClickedPeriodSelect(){
        viewModelScope.launch {
            _periodBottomSheetPage.emit(true)
        }
    }
    // 정산 내역 체크하러 가기
    fun onClickedNextPage() {
        viewModelScope.launch {
            _nextPage.emit(true)
        }
    }
    // 이전 페이지로
    fun onClickedBackPage(){
        viewModelScope.launch {
            _back.emit(true)
        }
    }
    // exit 버튼 클릭 -> 처음 정산하기 페이지
    fun onClickedExit() {
        viewModelScope.launch {
            _settlementPage.emit(true)
        }
    }

    // 날짜 선택 범위 text 세팅
    fun settingSelectDay(settingFormat: String, startSelectDay: String, endSelectDay: String){
        _startDay.value = startSelectDay
        _endDay.value = endSelectDay

        viewModelScope.launch(Dispatchers.IO) {
            _selectDay.postValue(settingFormat)
        }
    }
}