package com.aos.floney.view.mypage.alarm

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityLoginBinding
import com.aos.floney.databinding.ActivityMyPageAlarmBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.mypage.MyPageActivity
import com.aos.floney.view.mypage.inform.MyPageInformActivity
import com.aos.floney.view.password.find.PasswordFindActivity
import com.aos.floney.view.settleup.SettleUpActivity
import com.aos.floney.view.signup.SignUpActivity
import com.aos.model.alarm.UiAlarmGetModel
import com.aos.model.user.MyBooks
import com.aos.model.user.UiMypageSearchModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MyPageAlarmActivity : BaseActivity<ActivityMyPageAlarmBinding, MyPageAlarmViewModel>(R.layout.activity_my_page_alarm), UiAlarmGetModel.OnItemClickListener {

    override fun onItemClick(item: UiAlarmGetModel) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }

    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(
                Activity.OVERRIDE_TRANSITION_CLOSE,
                R.anim.anim_slide_in_from_left_fade_in,
                android.R.anim.fade_out)
        } else {
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, android.R.anim.fade_out)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(
                Activity.OVERRIDE_TRANSITION_CLOSE,
                R.anim.anim_slide_in_from_left_fade_in,
                android.R.anim.fade_out)
        } else {
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, android.R.anim.fade_out)
        }
    }
    private fun setUpUi() {
        binding.setVariable(BR.vm, viewModel)
        binding.setVariable(BR.eventHolder, this@MyPageAlarmActivity)
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect {
                if(it) {
                    finish()
                }
            }
        }
        repeatOnStarted {
            viewModel.complete.collect {
                if(it) {
                    viewModel.getAlarmInform()
                }
            }
        }
    }
}