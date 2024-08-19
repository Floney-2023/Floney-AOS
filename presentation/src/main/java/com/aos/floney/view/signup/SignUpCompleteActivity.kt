package com.aos.floney.view.signup

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivitySignUpBinding
import com.aos.floney.databinding.ActivitySignUpCompleteBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.add.BookAddActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpCompleteActivity : BaseActivity<ActivitySignUpCompleteBinding, SignUpCompleteViewModel>(R.layout.activity_sign_up_complete) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.nextPage.collect() {
                if(it) {
                    startActivity(Intent(this@SignUpCompleteActivity, BookAddActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                }
            }
        }
    }
}