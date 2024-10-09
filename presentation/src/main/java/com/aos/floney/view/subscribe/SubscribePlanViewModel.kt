package com.aos.floney.view.subscribe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorCode
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.UiBookEntranceModel
import com.aos.usecase.bookadd.BooksEntranceUseCase
import com.aos.usecase.bookadd.BooksJoinUseCase
import com.aos.usecase.home.GetBookInfoUseCase
import com.aos.usecase.mypage.MypageSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribePlanViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil
): BaseViewModel() {


    // 구독 하기
    private var _subscribe = MutableEventFlow<Boolean>()
    val subscribe: EventFlow<Boolean> get() = _subscribe

    // 구독 해지하기 이동
    private var _resubscribe = MutableEventFlow<Boolean>()
    val resubscribe: EventFlow<Boolean> get() = _resubscribe

    // 구매내역 복원하기
    private var _subscribeRestore = MutableEventFlow<Boolean>()
    val subscribeRestore: EventFlow<Boolean> get() = _subscribeRestore

    // 구독 해지하기 이동
    private var _servicePage = MutableEventFlow<Boolean>()
    val servicePage: EventFlow<Boolean> get() = _servicePage

    // 구독 정보 화면 나가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back

    // 구독 정보 화면 나가기
    fun onClickedExit(){
        viewModelScope.launch {
            _back.emit(true)
        }
    }

    // 구독 내역 복원하기
    fun onClickPlanRestore(){
        viewModelScope.launch {
            _subscribeRestore.emit(true)
        }
    }

    // 구독 하기
    fun onClickSubscribe(){
        viewModelScope.launch {
            _subscribe.emit(true)
        }
    }

    // 서비스 이용 약관
    fun onClickService(){
        viewModelScope.launch {
            _servicePage.emit(true)
        }
    }
}