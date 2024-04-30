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
    private fun setUpUi() {
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
    }
}