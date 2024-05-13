package com.aos.floney.view.book.entrance

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.UiBookEntranceModel
import com.aos.model.home.UiBookInfoModel
import com.aos.usecase.bookadd.BooksEntranceUseCase
import com.aos.usecase.bookadd.BooksJoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookEntranceViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val booksJoinUseCase: BooksJoinUseCase,
    private val booksEntranceUseCase : BooksEntranceUseCase
): BaseViewModel() {

    // 가계부 이름
    var inviteCode = MutableLiveData<String>("")


    private var _bookInfo = MutableLiveData<UiBookEntranceModel>()
    val bookInfo: LiveData<UiBookEntranceModel> get() = _bookInfo

    // 가계부 코드 입력하는 화면으로 이동
    private var _codeInputPage = MutableEventFlow<Boolean>()
    val codeInputPage: EventFlow<Boolean> get() = _codeInputPage

    // 가계부 초대 받기 성공, 이동
    private var _newBookCreatePage = MutableEventFlow<Boolean>()
    val newBookCreatePage: EventFlow<Boolean> get() = _newBookCreatePage

    // 코드 복사
    private var _inviteCodeCopy = MutableEventFlow<Boolean>()
    val inviteCodeCopy: EventFlow<Boolean> get() = _inviteCodeCopy

    // 화면 나가기
    private var _inviteCodeExit = MutableEventFlow<Boolean>()
    val inviteCodeExit: EventFlow<Boolean> get() = _inviteCodeExit

    fun setinviteCode(code: String){
        viewModelScope.launch {
            booksEntranceUseCase(code).onSuccess {
                inviteCode.postValue(code)
                _bookInfo.postValue(it)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookEntranceViewModel)))
            }
        }
    }
    fun onClickInviteCodeCopy(){
        viewModelScope.launch {
            _inviteCodeCopy.emit(true)
        }
    }

    fun onClickEntrance(){
        if(inviteCode.value!!.isNotEmpty()) {
            // 참여 코드 전송
            viewModelScope.launch(Dispatchers.IO) {
                baseEvent(Event.ShowLoading)
                booksJoinUseCase(inviteCode.value!!).onSuccess {
                    // 참여 완료, 참여 가계부 키 설정
                    prefs.setString("bookKey", it.bookKey)
                    baseEvent(Event.HideLoading)

                    _newBookCreatePage.emit(true)
                }.onFailure {
                    baseEvent(Event.HideLoading)
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@BookEntranceViewModel)))
                }
            }
        } else {
            // 참여 코드가 비어 있을 경우
            baseEvent(Event.ShowToastRes(R.string.book_add_invite_check_request_empty_code))
        }
    }

    fun onClickedSkipBtn(){
        viewModelScope.launch {
            _inviteCodeExit.emit(true)
        }
    }

    fun onClickCodeInput(){
        viewModelScope.launch(Dispatchers.IO) {
            _codeInputPage.emit(true)
        }
    }
}