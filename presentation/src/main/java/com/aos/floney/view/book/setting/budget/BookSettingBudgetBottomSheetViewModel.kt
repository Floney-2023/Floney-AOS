package com.aos.floney.view.book.setting.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.formatNumber
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.BudgetItem
import com.aos.usecase.booksetting.BooksInfoBudgetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSettingBudgetBottomSheetViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val booksInfoBudgetUseCase: BooksInfoBudgetUseCase
): BaseViewModel() {


    var title = MutableLiveData<String>("")

    private var _monthBudget = MutableLiveData<String>("")
    val monthBudget: LiveData<String> get() = _monthBudget

    // 월 예산 설정
    private var _budgetSheet = MutableEventFlow<String>()
    val budgetSheet: EventFlow<String> get() = _budgetSheet

    // 금액
    var cost = MutableLiveData<String>("")

    // 요청 날짜
    var date = MutableLiveData<String>("")


    fun settingUi(item: BudgetItem, dateString: String){

        date.postValue(dateString)
        title.postValue(item.month+"월 예산")

        if (!item.money.equals("0원"))
            cost.postValue(item.money)
        else
            cost.postValue("")
    }

    // 초기 자산 설정
    fun onClickSaveButton(){
        viewModelScope.launch {
            booksInfoBudgetUseCase(
                prefs.getString("bookKey",""),
                settingCost(),
                date.value!! ).onSuccess {
                // 변경 완료 토스트 메세지
                if (cost.value!! == "")
                    _budgetSheet.emit("0원")
                else
                    _budgetSheet.emit(cost.value!!)
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
            return cost.value!!.replace(",", "").substring(0, cost.value!!.length - 2).toInt()
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
            cost.postValue("${s.toString().formatNumber()}원")
        }
    }
}