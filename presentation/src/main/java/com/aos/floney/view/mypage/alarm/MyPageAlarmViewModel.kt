package com.aos.floney.view.mypage.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.alarm.UiAlarmGetModel
import com.aos.model.settlement.Settlement
import com.aos.model.settlement.UiSettlementSeeModel
import com.aos.model.user.MyBooks
import com.aos.usecase.mypage.AlarmInformGetUseCase
import com.aos.usecase.mypage.AlarmUpdateGetUseCase
import com.aos.usecase.mypage.MypageSearchUseCase
import com.aos.usecase.settlement.SettlementSeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageAlarmViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val mypageSearchUseCase : MypageSearchUseCase,
    private val alarmInformGetUseCase : AlarmInformGetUseCase,
    private val alarmUpdateGetUseCase : AlarmUpdateGetUseCase
): BaseViewModel() {

    // 알람 조회 정보
    private var _alarmList = MutableLiveData<List<UiAlarmGetModel>>()
    val alarmList: LiveData<List<UiAlarmGetModel>> get() = _alarmList

    var index = MutableLiveData<Int>(0)

    // 알람 조회 정보
    private var _bookList = MutableLiveData<List<MyBooks>>()
    val bookList: LiveData<List<MyBooks>> get() = _bookList

    // 다음 정산 페이지
    private var _complete = MutableEventFlow<Boolean>()
    val complete: EventFlow<Boolean> get() = _complete

    // 다음 정산 페이지
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back


    init {
        index.value = 0
        searchMypageItems()
    }
    fun searchMypageItems()
    {
        viewModelScope.launch(Dispatchers.IO) {
            mypageSearchUseCase().onSuccess {
                _bookList.postValue(it.myBooks)
                _complete.emit(true)
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }
    // 알람 내역 불러오기
    fun getAlarmInform(){
            viewModelScope.launch(Dispatchers.IO) {
                _bookList.value?.let { books ->
                    if (index.value != null && index.value!! < books.size) {
                        val bookKey = books[index.value!!].bookKey
                        alarmInformGetUseCase(bookKey).onSuccess { alarmList ->
                            _alarmList.postValue(alarmList)
                            setAlarmUpdate()
                        }.onFailure {
                            baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                        }
                    }
                }
            }
    }
    // 알람 읽음 처리
    fun setAlarmUpdate()
    {
        viewModelScope.launch(Dispatchers.IO) {
            _bookList.value?.let { books ->
                if (index.value != null && index.value!! < books.size) {
                    val bookKey = books[index.value!!].bookKey
                    _alarmList.value?.map {
                        alarmUpdateGetUseCase(bookKey, it.id).onSuccess {

                        }.onFailure {
                            baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
                        }
                    }
                }
            }
        }
    }
    // exit 버튼 클릭 -> 처음 정산하기 페이지
    fun onClickedExit() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
    fun onClickFlow(flow: Int) {
        index.value = flow
        getAlarmInform()
    }
}