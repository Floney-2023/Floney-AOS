package com.aos.floney.view.login

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.base.BaseViewModel
import com.aos.floney.databinding.ActivityLoginBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.util.NetworkUtils
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.password.find.PasswordFindActivity
import com.aos.floney.view.signup.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    lateinit var googleResultLauncher: ActivityResultLauncher<Intent>
    lateinit var mAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()

         googleResultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account, account.idToken ?: "")
            } else {
                viewModel.baseEvent(BaseViewModel.Event.ShowToast("구글 로그인에 실패하였습니다."))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.existBook.collect {
                if (it) {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(
                            Activity.OVERRIDE_TRANSITION_OPEN,
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                    finish()
                } else {
                    startActivity(Intent(this@LoginActivity, BookAddActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(
                            Activity.OVERRIDE_TRANSITION_OPEN,
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                    finish()
                }
            }
        }
        repeatOnStarted {
            viewModel.clickPasswordFind.collect {
                if (it) {
                    startActivity(Intent(this@LoginActivity, PasswordFindActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(
                            Activity.OVERRIDE_TRANSITION_OPEN,
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.clickSignUp.collect {
                when (it) {
                    "normal" -> startActivity(
                        Intent(
                            this@LoginActivity,
                            SignUpActivity::class.java
                        )
                    )

                    "social" -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                SignUpActivity::class.java
                            ).putExtra("provider", viewModel.getSocialTempData().provider)
                                .putExtra("token", viewModel.getSocialTempData().token)
                                .putExtra("email", viewModel.getSocialTempData().email)
                                .putExtra("nickname", viewModel.getSocialTempData().nickname)
                        )
                    }

                }

                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(
                        Activity.OVERRIDE_TRANSITION_OPEN,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                } else {
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
        }

        repeatOnStarted {
            viewModel.clickKakao.collect {
                if (it) {
                    onClickedKakaoLogin()
                }
            }
        }

        repeatOnStarted {
            viewModel.clickGoogle.collect {
                if (it) {
                    onClickGoogleLogin()
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
                            if (user.kakaoAccount != null) {
                                viewModel.setSocialTempData(
                                    "kakao",
                                    token.accessToken,
                                    user.kakaoAccount!!.email ?: "",
                                    user.kakaoAccount!!.profile?.nickname ?: ""
                                )
                                viewModel.isAuthTokenCheck("kakao", token.accessToken)
                            } else {
                                viewModel.baseEvent(BaseViewModel.Event.ShowToast("카카오 로그인에 실패하였습니다."))
                            }
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
                            if (error != null) {
                                viewModel.baseEvent(BaseViewModel.Event.ShowToast("카카오 로그인에 실패하였습니다."))
                            } else if (user != null) {
                                if (token != null) {
                                    Timber.e("user $user")
                                    Timber.e("token $token")
                                    if (user.kakaoAccount != null) {
                                        viewModel.setSocialTempData(
                                            "kakao",
                                            token.accessToken,
                                            user.kakaoAccount!!.email ?: "",
                                            user.kakaoAccount!!.profile?.nickname ?: ""
                                        )
                                        viewModel.isAuthTokenCheck("kakao", token.accessToken)
                                    } else {
                                        viewModel.baseEvent(BaseViewModel.Event.ShowToast("카카오 로그인에 실패하였습니다."))
                                    }
                                } else {
                                    viewModel.baseEvent(BaseViewModel.Event.ShowToast("카카오 로그인에 실패하였습니다."))
                                }
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

    // 구글 로그인
    private fun onClickGoogleLogin() {
        Timber.e("onClickGoogleLogin")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("918730717655-fv6illgnhvl3ne5sha3qjjr9kbjuk4ri.apps.googleusercontent.com")
            .requestEmail()
            .requestProfile()
            .build()

        val googleClient = GoogleSignIn.getClient(this, gso)
        googleResultLauncher.launch(googleClient.signInIntent)

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, token: String) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        //로그인에 성공한다면 UI를 업데이트하고 계정 정보를 불러온다.
                        val user: FirebaseUser? = mAuth.currentUser
                        if(user != null) {
                            viewModel.setSocialTempData(
                                "google",
                                token,
                                user.email ?: "",
                                user.displayName ?: "",
                            )
                            viewModel.isAuthTokenCheck("google", token)
                        } else {
                            viewModel.baseEvent(BaseViewModel.Event.ShowToast("구글 로그인에 실패하였습니다."))
                        }
                    } else {
                        viewModel.baseEvent(BaseViewModel.Event.ShowToast("구글 로그인에 실패하였습니다."))
                    }
                })
    }
}