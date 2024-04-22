package com.aos.floney.view.book.setting.edit

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
import com.aos.usecase.booksetting.BooksDeleteUseCase
import com.aos.usecase.booksetting.BooksInfoSeeProfileUseCase
import com.aos.usecase.booksetting.BooksNameChangeUseCase
import com.aos.usecase.home.CheckUserBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSettingEditViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val prefs: SharedPreferenceUtil,
    private val booksNameChangeUseCase : BooksNameChangeUseCase,
    private val booksDeleteUseCase : BooksDeleteUseCase,
    private val booksInfoSeeProfileUseCase : BooksInfoSeeProfileUseCase,
    private val checkUserBookUseCase: CheckUserBookUseCase
): BaseViewModel() {

    // 프로필 보여지기 ON/OFF
    var profileCheck: LiveData<Boolean> = stateHandle.getLiveData("profileCheck")

    // 방장이면 True, 팀원이면 False
    var roleCheck: LiveData<Boolean> = stateHandle.getLiveData("roleCheck")

    // 가계부 프로필 이미지
    var profileImg: LiveData<String> = stateHandle.getLiveData("profileImg")

    // 가계부 개수
    var bookCount: LiveData<Int> = stateHandle.getLiveData("bookCount")

    // 가계부 이름
    var bookHintName: LiveData<String> = stateHandle.getLiveData("bookName")

    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 바뀔 가계부 이름
    var bookName = MutableLiveData<String>("")

    // 프로필 이미지 변경 페이지 이동
    private var _profileChangePage = MutableEventFlow<Boolean>()
    val profileChangePage: EventFlow<Boolean> get() = _profileChangePage

    // 가계부 삭제 페이지 이동
    private var _deletePage = MutableEventFlow<Boolean>()
    val deletePage: EventFlow<Boolean> get() = _deletePage

    // 가계부 삭제 팝업 이동
    private var _deletePopup = MutableEventFlow<Boolean>()
    val deletePopup: EventFlow<Boolean> get() = _deletePopup



    // 이전 페이지로 이동
    fun onClickPreviousPage()
    {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
    // 가계부 이름 변경 버튼 클릭
    fun onClickBookNameChange()
    {
        if(bookName.value!!.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                baseEvent(Event.ShowLoading)
                booksNameChangeUseCase(bookName.value!!, prefs.getString("bookKey","")).onSuccess {
                    // 닉네임 변경 성공
                    baseEvent(Event.ShowToastRes(R.string.mypage_main_inform_nickname_request_success))
                    baseEvent(Event.HideLoading)
                }.onFailure {
                    baseEvent(Event.HideLoading)
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                }
            }
        }else {
            // 비밀번호 비어있을 경우
            baseEvent(Event.ShowToastRes(R.string.book_setting_edit_request))
        }

    }
    // 프로필 변경 페이지 이동
    fun onClickProfileImgChange()
    {
        viewModelScope.launch {
            _profileChangePage.emit(true)
        }
    }
    // 내역 프로필 보기 변경
    fun onClickMarketingTerms()
    {
        viewModelScope.launch {
            if(prefs.getString("bookKey","").isNotEmpty()) {
                booksInfoSeeProfileUseCase(prefs.getString("bookKey",""),!(profileCheck.value!!)).onSuccess {
                }.onFailure {
                    baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                }
            }
        }
    }
    // 가계부 삭제
    fun onClickDelete()
    {
        viewModelScope.launch {
            _deletePopup.emit(true)
        }
    }

    fun deleteBook(){
        if (bookCount.value == 1){
            viewModelScope.launch {
                if(prefs.getString("bookKey","").isNotEmpty()) {
                    baseEvent(Event.ShowLoading)
                    booksDeleteUseCase(prefs.getString("bookKey","")).onSuccess {
                        baseEvent(Event.HideLoading)
                        settingBookKey()
                    }.onFailure {
                        baseEvent(Event.HideLoading)
                        baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                    }
                }
            }
        }
        else {
            baseEvent(Event.ShowToastRes(R.string.book_setting_exit_dialog_cancel_error))
        }
    }
    fun settingBookKey(){
        viewModelScope.launch {
            checkUserBookUseCase().onSuccess {
                if(it.bookKey != "") {
                    prefs.setString("bookKey", it.bookKey)
                    _deletePage.emit(true)
                } else {
                    _deletePage.emit(false)
                }
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

}