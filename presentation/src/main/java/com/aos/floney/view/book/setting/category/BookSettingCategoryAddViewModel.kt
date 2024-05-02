package com.aos.floney.view.book.setting.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.formatNumber
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.UiBookCategory
import com.aos.model.home.DayMoneyModifyItem
import com.aos.usecase.booksetting.BooksCategoryAddUseCase
import com.aos.usecase.booksetting.BooksCategoryDeleteUseCase
import com.aos.usecase.history.GetBookCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSettingCategoryAddViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val booksCategoryAddUseCase: BooksCategoryAddUseCase
) : BaseViewModel() {

    // 항목이름
    var name = MutableLiveData<String>("")

    // 이전 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back


    // 이전 페이지
    private var _completePage = MutableEventFlow<String>()
    val completePage: EventFlow<String> get() = _completePage

    // 자산, 지출, 수입, 이체
    var flow = MutableLiveData<String>("자산")

    fun onClickedExit() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 카테고리 추가
    fun onClickAddComplete() {
        if (name.value!="") {
            viewModelScope.launch(Dispatchers.IO) {
                booksCategoryAddUseCase(
                    bookKey = prefs.getString("bookKey", ""),
                    flow.value!!,
                    name.value!!
                ).onSuccess {
                    _completePage.emit(it.name)
                }.onFailure {
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookSettingCategoryAddViewModel)))
                }
            }
        } else{
            baseEvent(Event.ShowToast("항목 이름을 입력해주세요."))
        }

    }

    // 자산, 지출, 수입, 이체 클릭
    fun onClickFlow(type: String) {
        flow.postValue(type)
    }
}