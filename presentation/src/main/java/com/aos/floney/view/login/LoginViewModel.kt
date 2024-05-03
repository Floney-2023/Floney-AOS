package com.aos.floney.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseViewModel
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.util.EventFlow
import com.aos.floney.util.MutableEventFlow
import com.aos.model.book.Currency
import com.aos.model.book.CurrencyInform
import com.aos.model.book.getCurrencySymbolByCode
import com.aos.usecase.booksetting.BooksCurrencySearchUseCase
import com.aos.usecase.home.CheckUserBookUseCase
import com.aos.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val prefs: SharedPreferenceUtil,
    private val loginUseCase: LoginUseCase,
    private val checkUserBookUseCase: CheckUserBookUseCase,
    private val booksCurrencySearchUseCase : BooksCurrencySearchUseCase
): BaseViewModel() {

    var email = MutableLiveData<String>("")
    var password = MutableLiveData<String>("")

    // 회원가입 클릭
    private var _clickSignUp = MutableEventFlow<Boolean>()
    val clickSignUp: EventFlow<Boolean> get() = _clickSignUp

    // 가계부 존재 여부
    private var _existBook = MutableEventFlow<Boolean>()
    val existBook: EventFlow<Boolean> get() = _existBook

    // 회원가입 클릭
    private var _clickPasswordFind = MutableEventFlow<Boolean>()
    val clickPasswordFind: EventFlow<Boolean> get() = _clickPasswordFind

    // 로그인하기 클릭
    fun onClickLogin() {
        viewModelScope.launch {
            if(email.value!!.isNotEmpty()) {
                if(password.value!!.isNotEmpty()) {
                    baseEvent(Event.ShowLoading)
                    loginUseCase(email.value ?: "", password.value ?: "").onSuccess {
                        baseEvent(Event.HideLoading)
                        prefs.setString("accessToken", it.accessToken)
                        prefs.setString("refreshToken", it.refreshToken)

                        checkUserBooks()
                    }.onFailure {
                        baseEvent(Event.HideLoading)
                        baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@LoginViewModel)))
                    }
                } else {
                    // 비밀번호가 비어있음
                    baseEvent(Event.ShowToastRes(R.string.request_input_password))
                }
            } else {
                // 이메일이 비어있음
                baseEvent(Event.ShowToastRes(R.string.sign_up_request_email_hint))
            }
        }
    }

    // 유저 가계부 유효 확인
    private fun checkUserBooks() {
        viewModelScope.launch {
            checkUserBookUseCase().onSuccess {
                if(it.bookKey != "") {
                    prefs.setString("bookKey", it.bookKey)
                    searchCurrency()
                } else {
                    _existBook.emit(false)
                }
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@LoginViewModel)))
            }
        }
    }
    // 화폐 설정 조회
    fun searchCurrency(){
        viewModelScope.launch {
            booksCurrencySearchUseCase(prefs.getString("bookKey", "")).onSuccess {
                if(it.myBookCurrency != "") {
                    // 화폐 단위 저장
                    prefs.setString("symbol", getCurrencySymbolByCode(it.myBookCurrency))

                    _existBook.emit(true)
                } else {
                    baseEvent(Event.ShowToastRes(R.string.currency_error))
                }
            }.onFailure {
                baseEvent(Event.ShowToast(it.message.parseErrorMsg(this@LoginViewModel)))
            }
        }
    }

    // 비밀번호 찾기 클릭
    fun onClickFindPassword() {
        viewModelScope.launch {
            _clickPasswordFind.emit(true)
        }
    }

    // 회원가입 클릭
    fun onClickSignUp() {
        viewModelScope.launch {
            _clickSignUp.emit(true)
        }
    }

    // 카카오 로그인 클릭
    fun onClickKakaoLogin() {

    }

    // 구글 로그인 클릭
    fun onClickGoogleLogin() {

    }

    // 애플 로그인 클릭
    fun onClickAppleLogin() {

    }

}