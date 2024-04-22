package com.aos.floney.view.home

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityHomeBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.analyze.AnalyzeActivity
import com.aos.floney.view.book.setting.BookSettingActivity
import com.aos.floney.view.history.HistoryActivity
import com.aos.floney.view.mypage.MyPageActivity
import com.aos.floney.view.settleup.SettleUpActivity
import com.aos.model.home.DayMoney
import com.aos.model.home.DayMoneyModifyItem
import com.aos.model.home.MonthMoney
import com.aos.model.home.UiBookDayModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home),
    UiBookDayModel.OnItemClickListener {
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
        setUpBottomNavigation()
    }

    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this)
    }

    private fun setUpViewModelObserver() {
        viewModel.clickedShowType.observe(this) { showType ->
            when (showType) {
                "month" -> {
                    viewModel.initCalendarMonth()
                    fragmentManager.beginTransaction()
                        .replace(R.id.fl_container, HomeMonthTypeFragment()).commit()
                }

                "day" -> {
                    viewModel.initCalendarDay()
                    fragmentManager.beginTransaction()
                        .replace(R.id.fl_container, HomeDayTypeFragment()).commit()
                }
            }
        }

        repeatOnStarted {
            // 내역추가
            viewModel.clickedAddHistory.collect {
                startActivity(
                    Intent(
                        this@HomeActivity,
                        HistoryActivity::class.java
                    ).putExtra("date", viewModel.getClickDate())
                        .putExtra("nickname", viewModel.getMyNickname())
                )
                if (Build.VERSION.SDK_INT >= 34) {
                    overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                } else {
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
        }
        repeatOnStarted {
            // 가계부 설정 페이지 이동
            viewModel.settingPage.collect {
                if(it) {
                    startActivity(Intent(this@HomeActivity, BookSettingActivity::class.java))
                }
            }
        }
    }

    // 캘린더 아이템이 표시됨
    fun onClickCalendarItem(item: MonthMoney) {
        viewModel.onClickShowDetail(item)
    }

    // 일별내역 아이템 클릭
    fun onClickDayItem(item: DayMoney) {
        startActivity(
            Intent(
                this@HomeActivity,
                HistoryActivity::class.java
            ).putExtra(
                "dayItem", DayMoneyModifyItem(
                    id = item.id,
                    money = item.money,
                    description = item.description,
                    lineDate = viewModel.getClickDate(),
                    lineCategory = item.lineCategory,
                    lineSubCategory = item.lineSubCategory,
                    assetSubCategory = item.assetSubCategory,
                    exceptStatus = item.exceptStatus,
                    writerNickName = item.writerNickName
                )
            )
        )
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun onItemClick(item: DayMoney) {
        onClickDayItem(item)
    }

    private fun setUpBottomNavigation() {
        // 가운데 메뉴(제보하기)에 대한 터치 이벤트를 막기 위한 로직
        binding.bottomNavigationView.apply {
            menu.getItem(2).isEnabled = false
            selectedItemId = R.id.homeFragment
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
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
                R.id.mypageFragment -> {
                    startActivity(Intent(this, MyPageActivity::class.java))
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