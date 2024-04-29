package com.aos.floney.view.mypage.inform

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityMyPageInformBinding
import com.aos.floney.view.login.LoginActivity
import com.aos.floney.view.mypage.MyPageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageInformActivity :
    BaseActivity<ActivityMyPageInformBinding, MyPageInformViewModel>(R.layout.activity_my_page_inform) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
    }

    private fun setupJetpackNavigation() {
        // Intent로부터 데이터를 받음
        val provider = intent.getStringExtra("PROVIDER") ?: ""
        val nickname = intent.getStringExtra("NICKNAME") ?: ""

        // Bundle 객체 생성 및 데이터 추가
        val bundle = Bundle().apply {
            putString("PROVIDER", provider)
            putString("NICKNAME", nickname)
        }

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_mypage_email_container) as NavHostFragment
        navController = host.navController
        navController.navigate(R.id.myPageInformEmailSettingFragment, bundle)
    }
    fun startMyPageActivity(){
        startActivity(Intent(this, MyPageActivity::class.java))
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        finishAffinity()
    }
    fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        finishAffinity()
    }
}