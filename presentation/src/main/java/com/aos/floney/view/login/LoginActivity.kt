package com.aos.floney.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityLoginBinding
import com.aos.floney.view.signup.SignUpActivity
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        lifecycleScope.launch {
            viewModel.clickSignUp.observe(this@LoginActivity) {
                Timber.e("it $it")
                if(it) {
                    startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                }
            }
        }
    }
}