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
import com.aos.model.user.MyBooks
import com.aos.model.user.UiMypageSearchModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

import androidx.databinding.library.baseAdapters.BR
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.mypage.bookadd.create.MyPageBookCreateActivity
import com.aos.floney.view.mypage.bookadd.MypageBookAddSelectBottomSheetFragment
import com.aos.floney.view.mypage.bookadd.codeinput.MyPageBookCodeInputActivity
import com.aos.floney.view.settleup.SettleUpActivity

@AndroidEntryPoint
class MyPageActivity : BaseActivity<ActivityMyPageBinding, MyPageViewModel>(R.layout.activity_my_page), UiMypageSearchModel.OnItemClickListener {
    override fun onItemClick(item: MyBooks) {
        viewModel.settingBookKey(item.bookKey)
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
        repeatOnStarted {
            viewModel.searchMypageItems()
        }
        repeatOnStarted {
            viewModel.bookAddBottomSheet.collect { shouldShowBottomSheet ->
                if (shouldShowBottomSheet) {
                    val bottomSheetFragment = MypageBookAddSelectBottomSheetFragment { checked ->
                        val intentClass = if (checked) MyPageBookCreateActivity::class.java else MyPageBookCodeInputActivity::class.java
                        startActivity(Intent(this@MyPageActivity, intentClass))
                    }
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
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
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    finish()
                    false
                }
                R.id.analysisFragment -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    finish()
                    false
                }
                R.id.settleUpFragment -> {
                    val intent = Intent(this, SettleUpActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
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