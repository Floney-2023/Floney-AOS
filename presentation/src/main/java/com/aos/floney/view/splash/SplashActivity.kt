package com.aos.floney.view.splash

import android.app.Activity
import android.content.Intent
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
import com.aos.floney.view.home.HomeActivity
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
                    val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(intent)
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
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
}