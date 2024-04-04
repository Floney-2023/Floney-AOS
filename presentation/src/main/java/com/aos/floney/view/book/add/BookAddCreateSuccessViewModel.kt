package com.aos.floney.view.book.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookAddCreateSuccessViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): BaseViewModel() {

    // 가계부 참여코드
    var inviteCode: LiveData<String> = stateHandle.getLiveData("invitecode")

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 가계부 작성하러 가기
    private var _nextPage = MutableEventFlow<Boolean>()
    val nextPage: EventFlow<Boolean> get() = _nextPage

    // 가계부 작성하러 가기
    private var _inviteDailog = MutableEventFlow<Boolean>()
    val inviteDailog: EventFlow<Boolean> get() = _inviteDailog


    // 이전 페이지로 이동
    fun onClickPreviousPage() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 작성하러 가기 -> 홈 화면으로 이동
    fun onClickGoHomeActivity(){
        viewModelScope.launch {
            _nextPage.emit(true)
        }
    }

    // 친구 초대하기 -> BottomSheet
    fun onClickInviteFriend(){
        viewModelScope.launch {
            _inviteDailog.emit(true)
        }
    }
}