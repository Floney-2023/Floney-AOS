package com.aos.floney.view.settleup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.Settlement
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiSettlementSeeModel
import com.aos.model.user.MyBooks
import com.aos.usecase.settlement.BooksUsersUseCase
import com.aos.usecase.settlement.SettlementLastUseCase
import com.aos.usecase.settlement.SettlementSeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettleUpSeeViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val settlementSeeUseCase : SettlementSeeUseCase
): BaseViewModel() {

    // 특정 가계부 유저 리스트
    private var _settlementList = MutableLiveData<UiSettlementSeeModel>()
    val settlementList: LiveData<UiSettlementSeeModel> get() = _settlementList


    // 다음 정산 페이지
    private var _startPage = MutableEventFlow<Boolean>()
    val startPage: EventFlow<Boolean> get() = _startPage

    // 처음 정산하기 페이지
    private var _settlementDetailPage = MutableEventFlow<Long>()
    val settlementDetailPage: EventFlow<Long> get() = _settlementDetailPage


    init {
        getSettlementInform()
    }

    // 정산 내역 불러오기
    fun getSettlementInform(){
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            settlementSeeUseCase(prefs.getString("bookKey","")).onSuccess {
                // 불러오기 성공
                _settlementList.postValue(it)
                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }
    // exit 버튼 클릭 -> 처음 정산하기 페이지
    fun onClickedExit() {
        viewModelScope.launch {
            _startPage.emit(true)
        }
    }

    // 정산 내역 클릭 시, 정산 내역 detail 하게 보여주기
    fun seeDetailSettlement(settlement: Settlement)
    {
        viewModelScope.launch {
            _settlementDetailPage.emit(settlement.id)
        }
    }

}