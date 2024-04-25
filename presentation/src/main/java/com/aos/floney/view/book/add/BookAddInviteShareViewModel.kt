package com.aos.floney.view.book.add

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.bookadd.BooksJoinUseCase
import com.aos.usecase.booksetting.BooksCodeCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookAddInviteShareViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val booksCodeCheckUseCase: BooksCodeCheckUseCase
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

    init {
        settingInviteCode()
    }
    // 가계부 초대 코드 불러오기
    fun settingInviteCode(){
        viewModelScope.launch {
            booksCodeCheckUseCase(
                prefs.getString("bookKey","")).onSuccess {
                inviteCode.postValue(it.code)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

    // 코드 공유 버튼 클릭
    fun onClickInviteCodeShare(){
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

    // 초대 코드 복사
    fun onClickInviteCodeCopy(){
        viewModelScope.launch {
            _inviteCodeCopy.emit(true)
        }
    }
}