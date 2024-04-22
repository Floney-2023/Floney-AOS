package com.aos.floney.view.analyze

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.BudgetItem
import com.aos.model.book.UiBookBudgetModel
import com.aos.usecase.booksetting.BooksBudgetCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AnalyzeSettingBudgetViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val booksBudgetCheckUseCase : BooksBudgetCheckUseCase
): BaseViewModel() {

    // 예산 리스트
    private var _budgetList = MutableLiveData<UiBookBudgetModel>()
    val budgetList: LiveData<UiBookBudgetModel> get() = _budgetList

    // 이전 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 처음 정산하기 페이지
    private var _budgetSettingPage = MutableEventFlow<BudgetItem>()
    val budgetSettingPage: EventFlow<BudgetItem> get() = _budgetSettingPage

    private var _year = MutableLiveData<String>()
    val year: LiveData<String> get() = _year

    init {
        // 예산 내역 불러오기
        getBudgetInform()
    }

    fun getBudgetInform(){
        // 현재 년도 설정
        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        val yearStringFormat = String.format("%s-01-01", currentYear)


        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            booksBudgetCheckUseCase(prefs.getString("bookKey",""),yearStringFormat).onSuccess {
                // 불러오기 성공
                _budgetList.postValue(it)
                _year.postValue(currentYear)
                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }
    // 이전 페이지
    fun onClickedExit() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 정산 내역 클릭 시, 정산 내역 detail 하게 보여주기
    fun settingBudget(budgetitem: BudgetItem) {
        viewModelScope.launch {
            _budgetSettingPage.emit(budgetitem)
        }
    }
    fun convertToDateString(month: String): String {
        val formattedMonth = if (month.length == 1) "0$month" else month
        return "${year.value}-$formattedMonth-01"
    }

    fun updateBudget(budgetItem: BudgetItem, budgetMoney: String){
        viewModelScope.launch {
            val budgetInfo = budgetList.value ?: return@launch // budgetList가 null이면 함수 종료

            val updatedList = budgetInfo.budgetList.map { item ->
                if (item.month == budgetItem.month) {
                    item.copy(money = budgetMoney, isExist = !budgetMoney.equals("0원"))
                } else {
                    item // 변경 없음
                }
            }

            _budgetList.postValue(UiBookBudgetModel(updatedList))
        }
    }
}