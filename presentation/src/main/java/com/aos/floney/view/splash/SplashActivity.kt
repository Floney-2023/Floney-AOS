package com.aos.floney.view.splash

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivitySplashBinding
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.view.login.LoginActivity
import com.aos.floney.view.onboard.OnBoardActivity
import com.aos.data.util.CurrencyUtil
import com.aos.floney.view.book.entrance.BookEntranceActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.settleup.SettleUpActivity
import com.aos.floney.view.signup.SignUpCompleteActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSplashAnimation()
        CurrencyUtil.currency = sharedPreferenceUtil.getString("symbol", "원")
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }
    // logo animation
    private fun setupSplashAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        binding.ivAppLogo.startAnimation(animation)

        // 2초 후 처음 실행할 경우 온보드 아니면 로그인으로 이동
        Handler(Looper.myLooper()!!).postDelayed({
            if (sharedPreferenceUtil.getBoolean(getString(R.string.is_first), true)) {
                val intent = Intent(this@SplashActivity, OnBoardActivity::class.java)
                startActivity(intent)
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                } else {
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            } else {
                // 자동 로그인 기능 구현
                if(sharedPreferenceUtil.getString("accessToken", "") != "" && sharedPreferenceUtil.getString("bookKey", "") == "") {
                    val intent = Intent(this@SplashActivity, SignUpCompleteActivity::class.java)
                    startActivity(intent)
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                } else if(sharedPreferenceUtil.getString("accessToken", "") != "") {
                    handleIntent(intent)
                }  else {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                }
            }
            finish()
        }, 2000)
    }
    private fun handleIntent(intent: Intent) {
        val data: Uri? = intent.data
        if (data != null) {
            navigateToAppropriateActivity(data)
        } else {
            // 딥 링크가 없을 경우 홈 화면으로 이동
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            if (Build.VERSION.SDK_INT >= 34) {
                overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
            } else {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    private fun navigateToAppropriateActivity(data: Uri) {
        data?.let {
            val campaign = it.getQueryParameter("campaign")
            when (campaign) {
                "floney_share" -> {
                    val inviteCode = it.getQueryParameter("inviteCode")
                    val intent = Intent(this@SplashActivity, BookEntranceActivity::class.java)

                    // 데이터를 Intent에 추가
                    intent.putExtra("settlementId", it.getQueryParameter("inviteCode"))

                    startActivity(intent)
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                }
                "floney_settlement_share" -> {

                    val intent = Intent(this@SplashActivity, SettleUpActivity::class.java)

                    // 데이터를 Intent에 추가
                    intent.putExtra("settlementId", it.getQueryParameter("settlementId"))
                    intent.putExtra("bookKey", it.getQueryParameter("bookKey"))

                    startActivity(intent)
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                }
            }
        }
    }
}