package com.aos.floney.view.settleup

import android.content.Intent
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivitySettleUpBinding
import com.aos.floney.databinding.ActivitySignUpBinding
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettleUpActivity : BaseActivity<ActivitySettleUpBinding, SettleUpViewModel>(R.layout.activity_settle_up) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
    }
    private fun setupJetpackNavigation() {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = host.navController

    }

    // 회원가입 완료 후 로그인 페이지로 이동
    fun startSettleUpActivity() {
        startActivity(Intent(this, SettleUpActivity::class.java))
        finishAffinity()
    }

    fun startBookAddActivity() {
        startActivity(Intent(this, BookAddActivity::class.java))
        finishAffinity()
    }

}