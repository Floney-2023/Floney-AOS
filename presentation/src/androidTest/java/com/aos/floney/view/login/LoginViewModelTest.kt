package com.aos.floney.view.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aos.data.entity.response.user.PostLoginEntity
import com.aos.data.util.SharedPreferenceUtil
import com.aos.model.user.PostLoginModel
import com.aos.usecase.booksetting.BooksCurrencySearchUseCase
import com.aos.usecase.home.CheckUserBookUseCase
import com.aos.usecase.login.AuthTokenCheckUseCase
import com.aos.usecase.login.LoginUseCase
import com.aos.usecase.login.SocialLoginUseCase
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var prefs: SharedPreferenceUtil
    @Mock
    private lateinit var loginUseCase: LoginUseCase
    @Mock
    private lateinit var checkUserBookUseCase: CheckUserBookUseCase
    @Mock
    private lateinit var booksCurrencySearchUseCase: BooksCurrencySearchUseCase
    @Mock
    private lateinit var authTokenCheckUseCase: AuthTokenCheckUseCase
    @Mock
    private lateinit var socialLoginUseCase: SocialLoginUseCase

    private lateinit var viewModel: LoginViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = LoginViewModel(prefs, loginUseCase, checkUserBookUseCase, booksCurrencySearchUseCase, authTokenCheckUseCase, socialLoginUseCase)
    }


    //onClickLogin with valid credentials emits success
    @Test
    suspend fun `onClickLogin_with_valid_credentials_emits_success`() {
        // 세팅
        viewModel.email.value = "test@example.com"
        viewModel.password.value = "password123"

        // 성공적인 로그인 시나리오를 위한 stubbing
        `when`(loginUseCase(viewModel.email.value!!, viewModel.password.value!!))
            .thenReturn(Result.success(PostLoginModel("accessToken", "refreshToken")))

        // 행동
        viewModel.onClickLogin()

        // 검증
        verify(prefs).setString("accessToken", "accessToken")
        verify(prefs).setString("refreshToken", "refreshToken")
    }

    @Test
    fun getEmail() {
    }

    @Test
    fun setEmail() {
    }

    @Test
    fun getPassword() {
    }

    @Test
    fun setPassword() {
    }

    @Test
    fun getClickSignUp() {
    }

    @Test
    fun getExistBook() {
    }

    @Test
    fun getClickPasswordFind() {
    }

    @Test
    fun getClickKakao() {
    }

    @Test
    fun getClickGoogle() {
    }

    @Test
    fun onClickLogin() {
    }

    @Test
    fun isAuthTokenCheck() {
    }

    @Test
    fun setSocialTempData() {
    }

    @Test
    fun getSocialTempData() {
    }

    @Test
    fun onClickFindPassword() {
    }

    @Test
    fun onClickSignUp() {
    }

    @Test
    fun onClickKakaoLogin() {
    }

    @Test
    fun onClickGoogleLogin() {
    }

    @Test
    fun onClickAppleLogin() {
    }
}