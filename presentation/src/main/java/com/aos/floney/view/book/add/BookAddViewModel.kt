package com.aos.floney.view.book.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.usecase.bookadd.BooksJoinUseCase
import com.aos.usecase.signup.CheckEmailCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookAddViewModel @Inject constructor(

    private val booksJoinUseCase: BooksJoinUseCase
): BaseViewModel() {

    // 입력한 참여 코드
    var code = MutableLiveData<String>("")


    // 다음 페이지 이동
    private var _nextPage = MutableEventFlow<Boolean>()
    val nextPage: EventFlow<Boolean> get() = _nextPage

    fun onClickInputComplete() {
        if(code.value!!.isNotEmpty()) {
                // 참여 코드 전송
                viewModelScope.launch(Dispatchers.IO) {
                    baseEvent(Event.ShowLoading)
                    booksJoinUseCase(code.value!!).onSuccess {
                        // 참여 완료, 참여 가계부 키 설정
                        it.bookKey

                        baseEvent(Event.HideLoading)

                        _nextPage.emit(true)
                    }.onFailure {
                        baseEvent(Event.HideLoading)
                        baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                    }
                }
        } else {
            // 참여 코드가 비어 있을 경우
            baseEvent(Event.ShowToastRes(R.string.book_add_invite_check_request_empty_code))
        }
    }

}