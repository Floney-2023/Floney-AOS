package com.aos.floney.view.home

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityHomeBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.analyze.AnalyzeActivity
import com.aos.floney.view.analyze.ChoiceDatePickerBottomSheet
import com.aos.floney.view.book.setting.BookSettingActivity
import com.aos.floney.view.history.HistoryActivity
import com.aos.floney.view.mypage.MyPageActivity
import com.aos.floney.view.settleup.SettleUpActivity
import com.aos.model.home.DayMoney
import com.aos.model.home.DayMoneyModifyItem
import com.aos.model.home.MonthMoney
import com.aos.model.home.UiBookDayModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import com.aos.floney.BuildConfig.google_app_reward_key
import com.aos.floney.view.common.WarningPopupDialog
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home),
    UiBookDayModel.OnItemClickListener {
    private val fragmentManager = supportFragmentManager
    private var mRewardAd: RewardedAd? = null

    override fun onResume() {
        super.onResume()
        val prefs = SharedPreferenceUtil(this)
        lifecycleScope.launch {
            viewModel.getBookInfo(prefs.getString("bookKey", ""))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpAdMob()
        setUpUi()
        setUpViewModelObserver()
        setUpBottomNavigation()
        setUpAccessCheck()
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
                    overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.slide_in, R.anim.slide_out_down)
                } else {
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out_down)
                }
            }
        }
        repeatOnStarted {
            // 가계부 설정 페이지 이동
            viewModel.settingPage.collect {
                if(it) {
                    if (mRewardAd != null) {
                        mRewardAd?.show(this@HomeActivity, OnUserEarnedRewardListener {
                            fun onUserEarnedReward(rewardItem: RewardItem) {
                                val rewardAmount = rewardItem.amount
                                var rewardType = rewardItem.type

                            }
                        })
                        mRewardAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                viewModel.updateAdvertiseTenMinutes()
                                goToBookSettingActivity()
                                mRewardAd = null
                                setUpAdMob()
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                mRewardAd = null
                            }
                        }
                    }
                } else {
                    goToBookSettingActivity()
                }
            }
        }
        repeatOnStarted {
            // 가계부 설정 페이지 이동
            viewModel.clickedChoiceDate.collect {
                ChoiceDatePickerBottomSheet(this@HomeActivity, it) {
                    // 결과값
                    val item = it.split("-")
                    viewModel.updateCalendarClickedItem(item[0].toInt(), item[1].toInt(), 1)
                }.show()
            }
        }
        repeatOnStarted {
            // 접근 불가 가계부 dialog
            viewModel.accessCheck.collect {
                if(it) {
                    val exitDialogFragment = WarningPopupDialog(
                        getString(R.string.home_dialog_title),
                        getString(R.string.home_dialog_info),
                        getString(R.string.home_dialog_right_button),
                        getString(R.string.home_dialog_right_button),
                        true
                    ) { checked ->

                    }
                    exitDialogFragment.show(fragmentManager, "clickDialog")
                }

            }
        }
    }
    fun goToBookSettingActivity()
    {
        startActivity(Intent(this@HomeActivity, BookSettingActivity::class.java))
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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
                    writerNickName = item.writerNickName,
                    repeatDuration = item.repeatDuration
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
    private fun setUpAdMob(){
        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        binding.adBanner.loadAd(adRequest)

        RewardedAd.load(this,google_app_reward_key, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mRewardAd = null
            }

            override fun onAdLoaded(ad: RewardedAd) {
                mRewardAd = ad
            }
        })
    }
    private fun setUpAccessCheck(){
        viewModel.setAccessCheck(intent.getBooleanExtra("accessCheck", false))
    }
}