package com.aos.floney.view.mypage.bookadd.codeinput

import androidx.lifecycle.MutableLiveData
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
import javax.inject.Inject

@HiltViewModel
class MyPageBookAddInviteSuccessViewModel @Inject constructor(): BaseViewModel() {

    // 다음 페이지 이동
    private var _nextPage = MutableEventFlow<Boolean>()
    val nextPage: EventFlow<Boolean> get() = _nextPage

    // 가계부에 초대됨 -> 홈 화면으로 이동
    fun onClickGoHomeActivity() {
        viewModelScope.launch {
            _nextPage.emit(true)
        }
    }
}