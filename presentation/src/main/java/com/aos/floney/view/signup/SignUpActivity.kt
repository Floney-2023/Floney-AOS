package com.aos.floney.view.signup

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivitySignUpBinding
import com.aos.floney.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignUpActivity() :
    BaseActivity<ActivitySignUpBinding, SignUpViewModel>(R.layout.activity_sign_up) {
    private lateinit var navController: NavController

    override fun onStart() {
        super.onStart()
        viewModel.setSocialUserModel(
            intent.getStringExtra("provider") ?: "",
            intent.getStringExtra("token") ?: "",
            intent.getStringExtra("email") ?: "",
            intent.getStringExtra("nickname") ?: ""
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
    }

    private fun setupJetpackNavigation() {

        val host =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = host.navController

        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.sign_up_nav)

        val destination = if(intent.getStringExtra("email")  != null) {
            R.id.signUpInputInfoFragment
        } else {
            R.id.signUpAgreeFragment
        }

        navGraph.setStartDestination(destination)
        navController.graph = navGraph
    }

    // 회원가입 완료 후 로그인 페이지로 이동
    fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(
                Activity.OVERRIDE_TRANSITION_OPEN,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        } else {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        finishAffinity()
    }

    fun startBookAddActivity() {
        startActivity(Intent(this, SignUpCompleteActivity::class.java))
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(
                Activity.OVERRIDE_TRANSITION_OPEN,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        } else {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        finishAffinity()
    }
}