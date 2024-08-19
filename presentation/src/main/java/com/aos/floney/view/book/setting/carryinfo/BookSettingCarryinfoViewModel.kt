package com.aos.floney.view.book.setting.carryinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.booksetting.BooksInfoCarryOverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookSettingCarryinfoViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val booksInfoCarryOverUseCase: BooksInfoCarryOverUseCase
): BaseViewModel() {

    // 가계부 초대 코드
    private var _carryInfo = MutableLiveData<Boolean>()
    val carryInfo : LiveData<Boolean> get() = _carryInfo

    // 초기 자산 설정
    private var _carryOverSheet = MutableEventFlow<Boolean>()
    val carryOverSheet: EventFlow<Boolean> get() = _carryOverSheet

    // 이월 내역 설정
    fun setCarryInfo(carryInfo : Boolean)
    {
        _carryInfo.value = carryInfo
    }
    fun onClickedCarryInfoNo(){
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _carryInfo.postValue(false)
            }
            postCarryInfo()
        }
    }
    fun onClickedCarryInfoYes(){
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _carryInfo.postValue(true)
            }
            postCarryInfo()
        }
    }
    private fun postCarryInfo(){
        viewModelScope.launch {
            booksInfoCarryOverUseCase(
                carryInfo.value!!,
                prefs.getString("bookKey","")).onSuccess {
                _carryOverSheet.emit(true)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookSettingCarryinfoViewModel)))
            }
        }
    }
}