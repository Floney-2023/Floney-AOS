package com.aos.floney.view.mypage

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityMyPageBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.mypage.inform.email.login.version.MyPageInformEmailActivity
import com.aos.floney.view.mypage.setting.MyPageSettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageActivity : BaseActivity<ActivityMyPageBinding, MyPageViewModel>(R.layout.activity_my_page) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.informPage.collect {
                if(it) {
                    startActivity(Intent(this@MyPageActivity, MyPageInformEmailActivity::class.java))
                }
            }
        }
        repeatOnStarted {
            viewModel.settingPage.collect {
                if(it) {
                    startActivity(Intent(this@MyPageActivity, MyPageSettingActivity::class.java))
                }
            }
        }
    }
}