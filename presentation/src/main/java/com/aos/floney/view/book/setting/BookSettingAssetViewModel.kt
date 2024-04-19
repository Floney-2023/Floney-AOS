package com.aos.floney.view.book.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.formatNumber
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.bookadd.BooksJoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSettingAssetViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): BaseViewModel() {

    // 가계부 초대 코드
    var inviteCode = MutableLiveData<String>("")

    // 코드 공유
    private var _inviteCodeShare = MutableEventFlow<Boolean>()
    val inviteCodeShare: EventFlow<Boolean> get() = _inviteCodeShare

    // bottomSheet 내리기
    private var _inviteCodeExit = MutableEventFlow<Boolean>()
    val inviteCodeExit: EventFlow<Boolean> get() = _inviteCodeExit

    // 코드 복사
    private var _inviteCodeCopy = MutableEventFlow<Boolean>()
    val inviteCodeCopy: EventFlow<Boolean> get() = _inviteCodeCopy

    // 금액
    var cost = MutableLiveData<String>("")

    // 코드 공유 버튼 클릭
    fun onClickSaveButton(){
        viewModelScope.launch {
            _inviteCodeShare.emit(true)
        }
    }
    // bottomsheet 내리기
    fun onClickedSkipBtn(){
        viewModelScope.launch {
            _inviteCodeExit.emit(true)
        }
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