package com.aos.floney.view.analyze

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityAnalyzeBinding
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.home.HomeDayTypeFragment
import com.aos.floney.view.mypage.MyPageActivity
import com.aos.floney.view.settleup.SettleUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyzeActivity : BaseActivity<ActivityAnalyzeBinding, AnalyzeViewModel>(R.layout.activity_analyze) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBottomNavigation()
        setUpViewModelObserver()
    }
    private fun setUpBottomNavigation() {
        // 가운데 메뉴(제보하기)에 대한 터치 이벤트를 막기 위한 로직
        binding.bottomNavigationView.apply {
            menu.getItem(2).isEnabled = false
            selectedItemId = R.id.analysisFragment
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
                R.id.mypageFragment -> {
                    startActivity(Intent(this, MyPageActivity::class.java))
                    if (Build.VERSION.SDK_INT >= 34) {
                        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                    } else {
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
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
    
    private fun setUpViewModelObserver() {
        viewModel.flow.observe(this) {
            when(it) {
                "지출" -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
                        .replace(R.id.fl_container, AnalyzeOutComeFragment()).commit()
                }
                "수입" -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
                        .replace(R.id.fl_container, AnalyzeIncomeFragment()).commit()
                }
                "예산" -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
                        .replace(R.id.fl_container, AnalyzePlanFragment()).commit()
                }
                "자산" -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
                        .replace(R.id.fl_container, AnalyzeAssetFragment()).commit()
                }
            }
        }
    }
}