package com.aos.floney.view.settleup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.settlement.UiSettlementAddModel
import com.aos.model.settlement.settleOutcomes
import com.aos.usecase.settlement.BooksOutComesUseCase
import com.aos.usecase.settlement.BooksUsersUseCase
import com.aos.usecase.settlement.SettlementAddUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettleUpCompleteViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val settlementAddUseCase : SettlementAddUseCase
): BaseViewModel() {


    var memberArray: LiveData<Array<String>> = stateHandle.getLiveData("member")
    var startDate: LiveData<String> = stateHandle.getLiveData("startDay")
    var endDate: LiveData<String> = stateHandle.getLiveData("endDay")
    var outcome: LiveData<LongArray> = stateHandle.getLiveData("outcome")
    var userEmail: LiveData<Array<String>> = stateHandle.getLiveData("userEmail")

    private var _settlementModel = MutableLiveData<UiSettlementAddModel>()
    val settlementModel: LiveData<UiSettlementAddModel> get() = _settlementModel

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
    private var _nextPage = MutableEventFlow< Array<settleOutcomes>>()
    val nextPage: EventFlow< Array<settleOutcomes>> get() = _nextPage

    // 처음 정산하기 페이지
    private var _settlementPage = MutableEventFlow<Boolean>()
    val settlementPage: EventFlow<Boolean> get() = _settlementPage


    init {
        getOutcomesItems()
    }
    fun getOutcomesItems(){
        val userEmails = memberArray.value!!.map { it }
        val outcomes = outcome.value!!.zip(userEmail.value!!) { outcome, email ->
            settleOutcomes(outcome, email)
        }
        Timber.e("yeah : ${outcomes}")
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            settlementAddUseCase(prefs.getString("bookKey",""), startDate.value!!, endDate.value!!,userEmails, outcomes).onSuccess {
                // 불러오기 성공
                _settlementModel.postValue(it)

                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }
    // 이전 페이지로
    fun onClickedSharePage(){

    }
    // exit 버튼 클릭 -> 처음 정산하기 페이지
    fun onClickedExit() {
        viewModelScope.launch {
            _settlementPage.emit(true)
        }
    }
}