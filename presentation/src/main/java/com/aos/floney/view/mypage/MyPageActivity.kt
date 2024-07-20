package com.aos.floney.view.mypage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityMyPageBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.analyze.AnalyzeActivity
import com.aos.floney.view.history.HistoryActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.home.HomeMonthTypeFragment
import com.aos.floney.view.mypage.alarm.MyPageAlarmActivity
import com.aos.floney.view.mypage.bookadd.MypageBookAddSelectBottomSheetFragment
import com.aos.floney.view.mypage.bookadd.codeinput.MyPageBookCodeInputActivity
import com.aos.floney.view.mypage.bookadd.create.MyPageBookCreateActivity
import com.aos.floney.view.mypage.inform.MyPageInformActivity
import com.aos.floney.view.mypage.main.service.MyPageServicePrivacyFragment
import com.aos.floney.view.mypage.main.service.MyPageServiceTermsFragment
import com.aos.floney.view.mypage.main.service.MyPageServiceTermsViewModel
import com.aos.floney.view.mypage.setting.MyPageSettingActivity
import com.aos.floney.view.settleup.SettleUpActivity
import com.aos.model.user.MyBooks
import com.aos.model.user.UiMypageSearchModel
import com.aos.model.user.UserModel.userNickname
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageActivity : BaseActivity<ActivityMyPageBinding, MyPageViewModel>(R.layout.activity_my_page) {
    private val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUi()
        setUpBottomNavigation()
        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 내역추가
            viewModel.clickedAddHistory.collect {
                startActivity(
                    Intent(
                        this@MyPageActivity,
                        HistoryActivity::class.java
                    ).putExtra("date", it)
                        .putExtra("nickname", userNickname)
                )
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.slide_in, R.anim.slide_out_down)
                } else {
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out_down)
                }
            }
        }
    }

    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@MyPageActivity)
    }

    fun startAlarmActivity() {
        startActivity(Intent(this, MyPageAlarmActivity::class.java))
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(
                Activity.OVERRIDE_TRANSITION_OPEN,
                R.anim.anim_slide_in_from_right_fade_in,
                android.R.anim.fade_out
            )
        } else {
            overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, android.R.anim.fade_out)
        }
    }
    fun startInformActivity(){
        startActivity(Intent(this, MyPageInformActivity::class.java))
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.anim_slide_in_from_right_fade_in, android.R.anim.fade_out)
        } else {
            overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, android.R.anim.fade_out)
        }
    }

    fun startSettingActivity() {
        startActivity(Intent(this, MyPageSettingActivity::class.java))
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.anim_slide_in_from_right_fade_in, android.R.anim.fade_out)
        } else {
            overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, android.R.anim.fade_out)
        }
    }

    fun startBottomSheet() {
        val bottomSheetFragment = MypageBookAddSelectBottomSheetFragment { checked ->
            val intentClass = if (checked) MyPageBookCreateActivity::class.java else MyPageBookCodeInputActivity::class.java
            startActivity(Intent(this, intentClass))
            if (Build.VERSION.SDK_INT >= 34) {
                overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
            } else {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
    private fun setUpBottomNavigation() {
        // 가운데 메뉴(제보하기)에 대한 터치 이벤트를 막기 위한 로직
        binding.bottomNavigationView.apply {
            menu.getItem(2).isEnabled = false
            selectedItemId = R.id.mypageFragment
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                    finish()
                    false
                }
                R.id.analysisFragment -> {
                    startActivity(Intent(this, AnalyzeActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                    finish()
                    false
                }
                R.id.settleUpFragment -> {
                    startActivity(Intent(this, SettleUpActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                    finish()
                    false
                }

                else -> false
            }
        }

        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {}
                R.id.analysisFragment -> {}
                R.id.settleUpFragment -> {}
                R.id.mypageFragment -> {}
            }
        }
    }
}