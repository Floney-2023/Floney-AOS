package com.aos.floney.view.book.setting.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.CurrencyUtil
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.Currency
import com.aos.model.book.CurrencyInform
import com.aos.model.book.UiBookCurrencyModel
import com.aos.model.book.getCurrencySymbolByCode
import com.aos.usecase.booksetting.BooksCurrencyChangeUseCase
import com.aos.usecase.booksetting.BooksCurrencySearchUseCase
import com.aos.usecase.booksetting.BooksInitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSettingCurrencyViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val booksInitUseCase : BooksInitUseCase,
    private val booksCurrencyChangeUseCase: BooksCurrencyChangeUseCase,
    private val booksCurrencySearchUseCase : BooksCurrencySearchUseCase
): BaseViewModel() {

    // 화폐 단위 리스트
    private var _currencyItems = MutableLiveData<UiBookCurrencyModel>()
    val currencyItems: LiveData<UiBookCurrencyModel> get() = _currencyItems

    // 이전 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    private var _init = MutableEventFlow<Boolean>()
    val init: EventFlow<Boolean> get() = _init


    init {
        getCurrencyInform()
    }

    // 화폐 단위 불러오기
    fun getCurrencyInform(){
        viewModelScope.launch {
            booksCurrencySearchUseCase(prefs.getString("bookKey", "")).onSuccess {
                if(it.myBookCurrency != "") {
                    // 화폐 단위 저장
                    prefs.setString("symbol", getCurrencySymbolByCode(it.myBookCurrency))
                    val cl = CurrencyInform()

                    val updatedCl = cl.map { currency ->
                        if (currency.code == it.myBookCurrency) {
                            currency.copy(isCheck = true)
                        } else {
                            currency
                        }
                    }
                    _currencyItems.postValue(UiBookCurrencyModel(updatedCl))
                } else {
                    baseEvent(Event.ShowToastRes(R.string.currency_error))
                }
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

    // 이전 페이지
    fun onClickPreviousPage() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
    // 가계부 초기화
    fun initBook(){
        viewModelScope.launch {
            if(prefs.getString("bookKey","").isNotEmpty()) {
                baseEvent(Event.ShowLoading)
                booksInitUseCase(prefs.getString("bookKey","")).onSuccess {
                    baseEvent(Event.HideLoading)
                    baseEvent(Event.ShowSuccessToast("화폐 단위가 변경 완료 되었습니다."))
                    _init.emit(true)
                }.onFailure {
                    baseEvent(Event.HideLoading)
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                }
            }
        }
    }
    // 화폐설정 변경
    fun settingCurrency(item : Currency){
        viewModelScope.launch {
            if(prefs.getString("bookKey","").isNotEmpty()) {
                baseEvent(Event.ShowLoading)
                booksCurrencyChangeUseCase(item.code, prefs.getString("bookKey","")).onSuccess {
                    baseEvent(Event.HideLoading)
                    prefs.setString("symbol",item.symbol) // 화폐 단위 변경
                    CurrencyUtil.currency = item.symbol

                    initBook() // 가계부 초기화
                }.onFailure {
                    baseEvent(Event.HideLoading)
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                }
            }
        }
    }
}