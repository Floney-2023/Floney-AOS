package com.aos.floney.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivitySplashBinding
import com.aos.floney.util.SharedPreferenceUtil
import com.aos.floney.view.login.LoginActivity
import com.aos.floney.view.onboard.OnBoardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSplashAnimation()
    }

    // logo animation
    private fun setupSplashAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        binding.ivAppLogo.startAnimation(animation)

        // 2초 후 처음 실행할 경우 온보드 아니면 로그인으로 이동
        Handler(Looper.myLooper()!!).postDelayed({
            if (SharedPreferenceUtil(this).getBoolean(getString(R.string.is_first), true)) {
                val intent = Intent(this@SplashActivity, OnBoardActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 2000)
    }
}