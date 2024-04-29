package com.aos.floney.view.mypage.service

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
import javax.inject.Inject

@HiltViewModel
class MyPageServicePrivacyViewModel @Inject constructor(
): BaseViewModel() {


    // 뒤로가기
    private var _back = MutableEventFlow<Boolean>()
    val back: EventFlow<Boolean> get() = _back


    // 이전 페이지로 이동
    fun onClickPreviousPage() {
        viewModelScope.launch {
            _back.emit(true)
        }
    }
}