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
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.Outcomes
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiOutcomesSelectModel
import com.aos.model.settlement.settleOutcomes
import com.aos.usecase.settlement.BooksOutComesUseCase
import com.aos.usecase.settlement.BooksUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettleUpOutcomesSelectViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val booksOutComesUseCase : BooksOutComesUseCase
): BaseViewModel() {


    var memberArray: LiveData<Array<String>> = stateHandle.getLiveData("member")
    var startDate: LiveData<String> = stateHandle.getLiveData("startDay")
    var endDate: LiveData<String> = stateHandle.getLiveData("endDay")


    private var _outcomesList = MutableLiveData<UiOutcomesSelectModel>()
    val outcomesList: LiveData<UiOutcomesSelectModel> get() = _outcomesList

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

    // bottomSheet 불러오기
    // 처음 정산하기 페이지
    private var _periodBottomSheetPage = MutableEventFlow<Boolean>()
    val periodBottomSheetPage: EventFlow<Boolean> get() = _periodBottomSheetPage

    init {
        getOutcomesItems()
    }
    fun getOutcomesItems(){
        val userEmails = memberArray.value!!.map { it }
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            booksOutComesUseCase(userEmails, startDate.value!!, endDate.value!!, prefs.getString("bookKey","")).onSuccess {
                // 불러오기 성공
                _outcomesList.postValue(it)
                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
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

    // 정산 내역 all check
    fun onClickedAllCheck()
    {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedList = _outcomesList.value?.outcomes?.map { user ->
                user.copy(isClick = true) // 선택된 내역의 isCheck를 true로 설정
            }
            _outcomesList.postValue(_outcomesList.value?.copy(outcomes = updatedList!!))
        }
    }
    fun onClickedNextPage() {
        viewModelScope.launch {
            val selectedEmails: Array<settleOutcomes> = _outcomesList.value?.outcomes
                ?.filter { it.isClick } // isCheck가 true인 사용자만 필터링
                ?.map { settleOutcomes(it.money, it.userEmail) } // 각 사용자의 이메일만 추출하여 리스트로 변환
                ?.toTypedArray() // 배열로 변환
                ?: emptyArray()

            if (selectedEmails.isNotEmpty()) {
                _nextPage.emit(selectedEmails)
            } else {
                baseEvent(Event.ShowToastRes(R.string.settle_up_member_select_error_messsage))
            }
        }
    }


    // 정산 내역 count
    fun settingSettlementOutcomes(bookUsers: Outcomes)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedList = _outcomesList.value?.outcomes?.map { user ->
                if (user.id == bookUsers.id) {
                    user.copy(isClick = !user.isClick) // 선택된 내역의 isCheck를 true로 설정
                } else {
                    user
                }
            }
            _outcomesList.postValue(_outcomesList.value?.copy(outcomes = updatedList!!))

        }
    }

}