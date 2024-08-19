package com.aos.floney.view.settleup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.BuildConfig
import com.aos.floney.BuildConfig.appsflyer_settlement_url
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.settlement.UiSettlementAddModel
import com.aos.model.settlement.settleOutcomes
import com.aos.usecase.settlement.BooksOutComesUseCase
import com.aos.usecase.settlement.BooksUsersUseCase
import com.aos.usecase.settlement.NaverShortenUrlUseCase
import com.aos.usecase.settlement.SettlementAddUseCase
import com.aos.usecase.settlement.SettlementDetailSeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.aos.floney.BuildConfig.naver_client_id
import com.aos.floney.BuildConfig.naver_client_secret

@HiltViewModel
class SettleUpDetailSeeViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val settlementDetailSeeUseCase : SettlementDetailSeeUseCase,
    private val naverShortenUrlUseCase : NaverShortenUrlUseCase
): BaseViewModel() {


    var id: LiveData<Long> = stateHandle.getLiveData("id")

    private var _settlementModel = MutableLiveData<UiSettlementAddModel>()
    val settlementModel: LiveData<UiSettlementAddModel> get() = _settlementModel

    // 이전 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 공유하기 페이지
    private var _sharedPage = MutableEventFlow<String>()
    val sharedPage: EventFlow<String> get() = _sharedPage


    init {
        getOutcomesItems()
    }
    fun getOutcomesItems(){
        Timber.e("yeah : ${id.value}")
        viewModelScope.launch(Dispatchers.IO) {
            settlementDetailSeeUseCase(id.value!!).onSuccess {
                // 불러오기 성공
                _settlementModel.postValue(it)

            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@SettleUpDetailSeeViewModel)))
            }
        }
    }
    // 공유하기
    fun onClickedSharePage(){
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            naverShortenUrlUseCase(naver_client_id, naver_client_secret, provideSettlementUrl()).onSuccess {
                // 불러오기 성공
                _sharedPage.emit(it.result)
                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@SettleUpDetailSeeViewModel)))
            }
        }
    }
    // exit 버튼 클릭 -> 이전 페이지
    fun onClickedExit() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
    // url 생성
    fun provideSettlementUrl(): String {
        return "https://floney.onelink.me${appsflyer_settlement_url}?settlementId=${settlementModel.value!!.id ?: ""}&bookKey=${prefs.getString("bookKey", "")}"
    }
}