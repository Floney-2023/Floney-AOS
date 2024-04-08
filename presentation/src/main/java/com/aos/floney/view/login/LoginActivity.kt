package com.aos.floney.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityLoginBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.password.find.PasswordFindActivity
import com.aos.floney.view.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.existBook.collect {
                if(it) {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@LoginActivity, BookAddActivity::class.java))
                    finish()
                }
            }
        }
        repeatOnStarted {
            viewModel.clickPasswordFind.collect {
                if(it) {
                    startActivity(Intent(this@LoginActivity, PasswordFindActivity::class.java))
                }
            }
        }

        repeatOnStarted {
            viewModel.clickSignUp.collect {
                if(it) {
                    startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                }
            }
        }
    }
}