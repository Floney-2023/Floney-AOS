package com.aos.floney.view.book.setting.asset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.formatNumber
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.booksetting.BooksInfoAssetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSettingAssetViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val booksInfoAssetUseCase: BooksInfoAssetUseCase
): BaseViewModel() {

    // 가계부 초대 코드
    var inviteCode = MutableLiveData<String>("")

    // 초기 자산 설정
    private var _initAssetSheet = MutableEventFlow<Boolean>()
    val initAssetSheet: EventFlow<Boolean> get() = _initAssetSheet

    // 금액
    var cost = MutableLiveData<String>("")

    // 초기 자산 설정
    fun onClickSaveButton(){
        viewModelScope.launch {
            if(cost.value!="") {
                booksInfoAssetUseCase(
                    prefs.getString("bookKey",""),
                    cost.value!!.replace(",", "").substring(0, cost.value!!.length - 2).toInt()).onSuccess {

                    baseEvent(Event.ShowToastRes(R.string.book_setting_bottom_asset_succcess))
                    _initAssetSheet.emit(true)
                }.onFailure {
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                }
            }
            else{
                baseEvent(Event.ShowToastRes(R.string.book_setting_bottom_asset_error))
            }
        }
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
            cost.postValue("${s.toString().formatNumber()}원")
        }
    }
}