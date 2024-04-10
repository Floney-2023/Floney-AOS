package com.aos.floney.view.settleup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.user.MyBooks
import com.aos.usecase.settlement.BooksUsersUseCase
import com.aos.usecase.settlement.SettlementLastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettleUpMemberSelectViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val booksUsersUseCase : BooksUsersUseCase
): BaseViewModel() {

    // 특정 가계부 유저 리스트
    private var _booksUsersList = MutableLiveData<UiMemberSelectModel>()
    val booksUsersList: LiveData<UiMemberSelectModel> get() = _booksUsersList


    // 다음 정산 페이지
    private var _nextPage = MutableEventFlow<Boolean>()
    val nextPage: EventFlow<Boolean> get() = _nextPage

    // 처음 정산하기 페이지
    private var _settlementPage = MutableEventFlow<Boolean>()
    val settlementPage: EventFlow<Boolean> get() = _settlementPage


    init {
        getSettlementInform()
    }

    // 가계부 인원 불러오기
    fun getSettlementInform(){
        viewModelScope.launch(Dispatchers.IO) {
            baseEvent(Event.ShowLoading)
            booksUsersUseCase(prefs.getString("bookKey","")).onSuccess {
                // 불러오기 성공
                _booksUsersList.postValue(it)
                baseEvent(Event.HideLoading)
            }.onFailure {
                baseEvent(Event.HideLoading)
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }
    // 정산하기 시작
    fun onClickedNextPage() {
        viewModelScope.launch {
            _nextPage.emit(true)
        }
    }

    // exit 버튼 클릭 -> 처음 정산하기 페이지
    fun onClickedExit() {
        viewModelScope.launch {
            _settlementPage.emit(true)
        }
    }

    // 멤버 클릭 시, 정산 멤버 count
    fun settingSettlementMember(bookUsers: BookUsers)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedList = _booksUsersList.value?.booksUsers?.map { user ->
                if (user.email == bookUsers.email) {
                    user.copy(isCheck = !user.isCheck) // 선택된 멤버의 isCheck를 true로 설정
                } else {
                    user
                }
            }
            _booksUsersList.postValue(_booksUsersList.value?.copy(booksUsers = updatedList!!))

        }
    }

}