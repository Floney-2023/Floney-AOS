package com.aos.floney.view.signup

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivitySignUpBinding
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.login.LoginActivity
import com.aos.model.user.SocialUserModel
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
        startActivity(Intent(this, BookAddActivity::class.java))
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