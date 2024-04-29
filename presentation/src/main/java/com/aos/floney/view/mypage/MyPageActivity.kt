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
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.home.HomeMonthTypeFragment
import com.aos.floney.view.mypage.bookadd.MypageBookAddSelectBottomSheetFragment
import com.aos.floney.view.mypage.bookadd.codeinput.MyPageBookCodeInputActivity
import com.aos.floney.view.mypage.bookadd.create.MyPageBookCreateActivity
import com.aos.floney.view.mypage.inform.MyPageInformActivity
import com.aos.floney.view.mypage.service.MyPageServicePrivacyFragment
import com.aos.floney.view.mypage.service.MyPageServiceTermsFragment
import com.aos.floney.view.mypage.service.MyPageServiceTermsViewModel
import com.aos.floney.view.mypage.setting.MyPageSettingActivity
import com.aos.floney.view.settleup.SettleUpActivity
import com.aos.model.user.MyBooks
import com.aos.model.user.UiMypageSearchModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageActivity : BaseActivity<ActivityMyPageBinding, MyPageViewModel>(R.layout.activity_my_page), UiMypageSearchModel.OnItemClickListener {
    private val fragmentManager = supportFragmentManager
    override fun onItemClick(item: MyBooks) {
        viewModel.settingBookKey(item.bookKey)
    }
    override fun onResume() {
        super.onResume()
        viewModel.searchMypageItems()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
        setUpBottomNavigation()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@MyPageActivity)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.informPage.collect {
                if(it) {
                    startActivity(Intent(this@MyPageActivity, MyPageInformActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                }
            }
        }
        repeatOnStarted {
            viewModel.settingPage.collect {
                if(it) {
                    startActivity(Intent(this@MyPageActivity, MyPageSettingActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                }
            }
        }
        repeatOnStarted {
            viewModel.bookAddBottomSheet.collect { shouldShowBottomSheet ->
                if (shouldShowBottomSheet) {
                    val bottomSheetFragment = MypageBookAddSelectBottomSheetFragment { checked ->
                        val intentClass = if (checked) MyPageBookCreateActivity::class.java else MyPageBookCodeInputActivity::class.java
                        startActivity(Intent(this@MyPageActivity, intentClass))
                        if (Build.VERSION.SDK_INT >= 34) {
                            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                        } else {
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }
                    }
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                }
            }
        }
        repeatOnStarted {
            viewModel.mailPage.collect {
                if (it){
                    val email = Intent(Intent.ACTION_SEND)
                    email.setType("plain/text")
                    val address = arrayOf("floney.team@gmail.com")
                    email.putExtra(Intent.EXTRA_EMAIL, address)
                    email.putExtra(Intent.EXTRA_SUBJECT, "Floney 문의 제보")
                    email.putExtra(Intent.EXTRA_TEXT, "작성자 : \n 문의 기능 : \n문의 내용 : \n")
                    startActivity(email)

                }
            }
        }
        repeatOnStarted {
            viewModel.privatePage.collect {
                if (it){
                    fragmentManager.beginTransaction()
                        .replace(R.id.fl_mypage_container, MyPageServicePrivacyFragment()).commit()
                }
            }
        }
        repeatOnStarted {
            viewModel.usageRightPage.collect {
                if (it){
                    fragmentManager.beginTransaction()
                        .replace(R.id.fl_mypage_container, MyPageServiceTermsFragment()).commit()
                }
            }
        }
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