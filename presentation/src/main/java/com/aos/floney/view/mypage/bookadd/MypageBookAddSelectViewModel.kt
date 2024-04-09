package com.aos.floney.view.mypage.bookadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.bookadd.BooksJoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MypageBookAddSelectViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): BaseViewModel() {

    // 가계부 초대 코드
    var inviteCode = MutableLiveData<String>("")

    // 가계부 생성하기 체크
    private var _bookCreateTerms = MutableLiveData<Boolean>(true)
    val bookCreateTerms: LiveData<Boolean> get() = _bookCreateTerms

    // 코드 입력하기 체크
    private var _codeInputTerms = MutableLiveData<Boolean>(false)
    val codeInputTerms: LiveData<Boolean> get() = _codeInputTerms

    // 추가 하기 버튼 클릭
    private var _addButton = MutableEventFlow<Boolean>()
    val addButton: EventFlow<Boolean> get() = _addButton

    // 가계부 생성하기
    fun onClickBookCreate(){
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _bookCreateTerms.postValue(true)
                _codeInputTerms.postValue(false)
            }
        }
    }

    // 코드 입력하기
    fun onClickCodeInput(){
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _bookCreateTerms.postValue(false)
                _codeInputTerms.postValue(true)
            }
        }
    }

    fun onClickAddButton(){
        viewModelScope.launch(Dispatchers.IO) {
            _addButton.emit(true)
        }
    }
}