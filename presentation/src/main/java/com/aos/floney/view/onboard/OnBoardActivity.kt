package com.aos.floney.view.onboard

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityOnBoardBinding
import com.aos.floney.view.login.LoginActivity
import com.aos.floney.view.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardActivity : BaseActivity<ActivityOnBoardBinding, OnBoardViewModel>(R.layout.activity_on_board) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.let {
            it.viewpaper2.adapter = OnBoardViewPaperAdapter(this@OnBoardActivity)
            binding.wormDotsIndicator.attachTo(it.viewpaper2)
        }
    }

    fun onChangeViewPagerItem(position: Int) {
        binding.viewpaper2.currentItem = position
    }

    fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        finish()
    }

}