package com.aos.floney.view.book.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.Currency
import com.aos.model.book.UiBookCurrencyModel
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.user.MyBooks
import com.aos.usecase.settlement.BooksUsersUseCase
import com.aos.usecase.settlement.SettlementLastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookSettingCurrencyViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val booksUsersUseCase : BooksUsersUseCase
): BaseViewModel() {

    // 화폐 단위 리스트
    private var _currencyItems = MutableLiveData<UiBookCurrencyModel>()
    val currencyItems: LiveData<UiBookCurrencyModel> get() = _currencyItems

    // 이전 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back


    init {
        getCurrencyInform()
    }

    // 가계부 인원 불러오기
    fun getCurrencyInform(){

        val cl = listOf(
            Currency("원", "KRW", true),
            Currency("$", "USD", false),
            Currency("€", "EUR", false),
            Currency("¥", "JPY", false),
            Currency("¥", "CNY", false),
            Currency("£", "GBP", false),
            Currency("đ", "VND", false),
            Currency("₱", "PHP", false),
            Currency("฿", "THB", false),
            Currency("Rp", "IDR", false),
            Currency("RM", "MYR", false),
            Currency("₺", "TRY", false),
            Currency("₽", "RUB", false),
            Currency("Ft", "HUF", false),
            Currency("Fr", "CHF", false),
            Currency("₹", "INR", false),
            Currency("zł", "PLN", false),
            Currency("Kč", "CZK", false),
            Currency("kr", "DKK", false),
            Currency("₦", "NGN", false)
        )

        viewModelScope.launch(Dispatchers.IO) {
            _currencyItems.postValue(UiBookCurrencyModel(cl))

        }
    }

    // 이전 페이지
    fun onClickPreviousPage() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 멤버 클릭 시, 정산 멤버 count
    fun settingCurrency(bookUsers: Currency)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedList = _currencyItems.value?.currencyList?.map { user ->
                if (user.country == bookUsers.country) {
                    user.copy(isCheck = !user.isCheck) // 선택된 멤버의 isCheck를 true로 설정
                } else {
                    user
                }
            }
            _currencyItems.postValue(_currencyItems.value?.copy(currencyList = updatedList!!))

        }
    }

}