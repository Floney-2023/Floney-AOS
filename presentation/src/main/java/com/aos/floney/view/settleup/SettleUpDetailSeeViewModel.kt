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
import com.aos.usecase.settlement.SettlementDetailSeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettleUpDetailSeeViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val settlementDetailSeeUseCase : SettlementDetailSeeUseCase
): BaseViewModel() {


    var id: LiveData<Long> = stateHandle.getLiveData("id")

    private var _settlementModel = MutableLiveData<UiSettlementAddModel>()
    val settlementModel: LiveData<UiSettlementAddModel> get() = _settlementModel

    // 이전 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 공유하기 페이지
    private var _sharedPage = MutableEventFlow<Boolean>()
    val sharedPage: EventFlow<Boolean> get() = _sharedPage


    init {
        getOutcomesItems()
    }
    fun getOutcomesItems(){
        Timber.e("yeah : ${id.value}")
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            settlementDetailSeeUseCase(id.value!!).onSuccess {
                // 불러오기 성공
                _settlementModel.postValue(it)

                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@SettleUpDetailSeeViewModel)))
            }
        }
    }
    // 공유하기
    fun onClickedSharePage(){
        viewModelScope.launch {
            _sharedPage.emit(true)
        }
    }
    // exit 버튼 클릭 -> 이전 페이지
    fun onClickedExit() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
}