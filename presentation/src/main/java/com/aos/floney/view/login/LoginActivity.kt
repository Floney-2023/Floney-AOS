package com.aos.floney.view.login

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.base.BaseViewModel
import com.aos.floney.databinding.ActivityLoginBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.util.NetworkUtils
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.mypage.MyPageActivity
import com.aos.floney.view.password.find.PasswordFindActivity
import com.aos.floney.view.settleup.SettleUpActivity
import com.aos.floney.view.signup.SignUpActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.existBook.collect {
                if(it) {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                    finish()
                } else {
                    startActivity(Intent(this@LoginActivity, BookAddActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                    finish()
                }
            }
        }
        repeatOnStarted {
            viewModel.clickPasswordFind.collect {
                if(it) {
                    startActivity(Intent(this@LoginActivity, PasswordFindActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.clickSignUp.collect {
                if(it) {
                    startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.clickKakao.collect {
                if(it) {
                    onClickedKakaoLogin()
                }
            }
        }
    }

    // 카카오 로그인
    private fun onClickedKakaoLogin() {
        if (NetworkUtils.isNetworkConnected(applicationContext)) {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    viewModel.baseEvent(BaseViewModel.Event.ShowToast("카카오 로그인에 실패하였습니다."))
                } else if (token != null) {
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            viewModel.baseEvent(BaseViewModel.Event.ShowToast("카카오 로그인에 실패하였습니다."))
                        } else if (user != null) {
                            Timber.e("user $user")
                            Timber.e("token ${token?.accessToken ?: "kmbn"}")
//                            LoginModel.login.email = user.kakaoAccount?.email.toString()
//                            LoginModel.login.nickname =
//                                user.kakaoAccount?.profile?.nickname.toString()
//                            LoginModel.login.profile =
//                                user.kakaoAccount?.profile?.profileImageUrl.toString()
//                            LoginModel.login.provider = "kakao"
//                            LoginModel.login.providerId = user.id.toString()
//
//                            loginViewModel.loginUser("KAKAO")
                        }
                    }
                }
            }
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(applicationContext)) {
                // 카카오톡으로 로그인
                UserApiClient.instance.loginWithKakaoTalk(applicationContext) { token, error ->
                    Timber.e("loginWithKakaoTalk error $error")
                    // token.accessToken 소셜 토큰

                    if (error != null) {
                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            Timber.e("error is ClientError error $error")
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(
                            applicationContext,
                            callback = callback
                        )
                    } else {
                        UserApiClient.instance.me { user, error ->
                            Timber.e("callback error2 $error")
                            Timber.e("callback user $user")
                            if (error != null) {
                                viewModel.baseEvent(BaseViewModel.Event.ShowToast("카카오 로그인에 실패하였습니다."))
                            } else if (user != null) {
                                Timber.e("token ${token?.accessToken ?: "kmbn"}")
                                Timber.e("user $user")
//                            LoginModel.login.email = user.kakaoAccount?.email.toString()
//                            LoginModel.login.nickname =
//                                user.kakaoAccount?.profile?.nickname.toString()
//                            LoginModel.login.profile =
//                                user.kakaoAccount?.profile?.profileImageUrl.toString()
//                            LoginModel.login.provider = "kakao"
//                            LoginModel.login.providerId = user.id.toString()
//
//                            loginViewModel.loginUser("KAKAO")
                            }
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    applicationContext,
                    callback = callback
                )
            }
        }


    }
}