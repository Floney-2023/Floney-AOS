package com.aos.floney.view.mypage.bookadd.create

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
import com.aos.usecase.bookadd.BooksCreateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageBookAddSettingProfileViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val booksCreateUseCase: BooksCreateUseCase
): BaseViewModel() {

    // 가계부 이름
    var bookName: LiveData<String> = stateHandle.getLiveData("bookname")

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 가계부 생성 완료하기
    private var _nextPage = MutableEventFlow<Boolean>()
    val nextPage: EventFlow<Boolean> get() = _nextPage

    // 가계부 이미지 Url
    private var _bookUrl = MutableLiveData<String>("")
    val bookUrl : LiveData<String> get() = _bookUrl

    // 가계부 초대코드
    var inviteCode = MutableLiveData<String>()

    // 이전 페이지로 이동
    fun onClickPreviousPage() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
    // 가계부 이름, 이미지 전송 -> 가계부 생성 -> 다음 페이지로 이동
    fun onClickNextPage(){
        if(bookName.value!!.isNotEmpty()) {
            // 가계부 이름, 이미지 전송 요청
            viewModelScope.launch(Dispatchers.IO) {
                baseEvent(Event.ShowLoading)
                booksCreateUseCase(bookName.value!!,bookUrl.value!!).onSuccess {
                    // 전송 성공
                    prefs.setString("bookKey", it.bookKey)
                    inviteCode.postValue(it.code)

                    baseEvent(Event.HideLoading)
                    _nextPage.emit(true)
                }.onFailure {
                    baseEvent(Event.HideLoading)
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@MyPageBookAddSettingProfileViewModel)))
                }
            }
        } else {
            // 이메일이 비어 있을 경우
            baseEvent(Event.ShowToastRes(R.string.book_add_new_book_name_button_text))
        }
    }

    // 이미지 설정
    fun onClickSettingImage(){

    }
}