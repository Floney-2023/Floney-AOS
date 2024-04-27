package com.aos.floney.view.book.setting.asset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.data.util.CurrencyUtil
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.formatNumber
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.analyze.PostAnalyzeAssetUseCase
import com.aos.usecase.booksetting.BooksCodeCheckUseCase
import com.aos.usecase.booksetting.BooksInfoAssetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@HiltViewModel
class BookSettingAssetViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val booksCodeCheckUseCase: BooksCodeCheckUseCase,
    private val booksInfoAssetUseCase: BooksInfoAssetUseCase,
    private val postAnalyzeAssetUseCase: PostAnalyzeAssetUseCase
): BaseViewModel() {

    // 가계부 초대 코드
    var inviteCode = MutableLiveData<String>("")

    // 초기 자산 설정
    private var _initAssetSheet = MutableEventFlow<Boolean>()
    val initAssetSheet: EventFlow<Boolean> get() = _initAssetSheet

    // 금액
    var cost = MutableLiveData<String>("")

    init {
        settingInitAsset()
    }

    // 초기 자산 불러오기
    fun settingInitAsset() {
        viewModelScope.launch {
            // Calendar 인스턴스 생성
            val calendar = Calendar.getInstance()
            // 현재 연도와 월을 가져옴
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH는 0부터 시작하므로 +1 해줌
            // "YYYY-MM" 형식으로 포맷
            val currentYearMonth = String.format("%d-%02d", year, month)

            postAnalyzeAssetUseCase(
                prefs.getString("bookKey",""), currentYearMonth).onSuccess {
                cost.postValue(it.initAsset)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

    // 초기 자산 설정
    fun onClickSaveButton(){
        viewModelScope.launch {
            booksInfoAssetUseCase(
                prefs.getString("bookKey",""),
                settingCost()).onSuccess {

                baseEvent(Event.ShowToastRes(R.string.book_setting_bottom_asset_succcess))
                _initAssetSheet.emit(true)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }
    fun settingCost(): Int {
        if (cost.value=="")
            return 0
        else if (cost.value!!.length<=4)
            return cost.value!!.substring(0, cost.value!!.length-1).toInt()
        else if (cost.value!="")
            return cost.value!!.replace(",", "").replace(CurrencyUtil.currency,"").toInt()
        else
            return 0
    }
    // bottomsheet 내리기
    fun onClickedInitBtn(){
        cost.postValue("")
    }

    // 숫자 변화 감지
    fun onCostTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (count == 0) {
            cost.postValue("${s.toString().formatNumber()}")
        } else {
            cost.postValue("${s.toString().formatNumber()}${CurrencyUtil.currency}")
        }
    }
}