package com.aos.floney.view.home

import androidx.lifecycle.viewModelScope
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.usecase.home.CheckUserBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val checkUserBookUseCase: CheckUserBookUseCase
): BaseViewModel() {

    init {
        checkUserBooks()
    }

    // 유저 가계부 유효 확인
    private fun checkUserBooks() {
        viewModelScope.launch {
            checkUserBookUseCase().onSuccess {
                Timber.e("bookKey ${it.bookKey}")
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg()))
            }
        }
    }

}