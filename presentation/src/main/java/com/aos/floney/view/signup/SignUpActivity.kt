package com.aos.floney.view.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivitySignUpBinding
import com.aos.floney.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>(R.layout.activity_sign_up) {
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
    fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

}