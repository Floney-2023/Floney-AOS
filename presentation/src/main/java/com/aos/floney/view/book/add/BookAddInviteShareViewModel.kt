package com.aos.floney.view.book.add

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
class BookAddInviteShareViewModel @Inject constructor(): BaseViewModel() {

    // 다음 페이지 이동
    private var _nextPage = MutableEventFlow<Boolean>()
    val nextPage: EventFlow<Boolean> get() = _nextPage
    fun onClickGoHomeActivity() {
        viewModelScope.launch {
            _nextPage.emit(true)
        }
    }
    // 코드 공유 버튼 클릭
    fun onClickInviteCodeShare(){

    }
    // bottomsheet 내리기
    fun onClickedSkipBtn(){

    }
}