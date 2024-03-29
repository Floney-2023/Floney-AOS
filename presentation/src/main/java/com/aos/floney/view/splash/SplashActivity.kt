package com.aos.floney.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSplashAnimation()
    }

    // logo animation
    private fun setupSplashAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        binding.ivAppLogo.startAnimation(animation)

        // 2초후 로그인 선택 화면으로 이동
        Handler(Looper.myLooper()!!).postDelayed({
        }, 2000)
    }
}