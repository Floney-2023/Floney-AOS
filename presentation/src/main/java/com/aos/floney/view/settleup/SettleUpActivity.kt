package com.aos.floney.view.settleup

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.data.util.CurrencyUtil
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.BuildConfig.appsflyer_dev_key
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivitySettleUpBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.analyze.AnalyzeActivity
import com.aos.floney.view.history.HistoryActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.login.LoginActivity
import com.aos.floney.view.mypage.MyPageActivity
import com.aos.model.user.UserModel.userNickname
import com.appsflyer.AppsFlyerLib
import com.appsflyer.deeplink.DeepLink
import com.appsflyer.deeplink.DeepLinkListener
import com.appsflyer.deeplink.DeepLinkResult
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SettleUpActivity : BaseActivity<ActivitySettleUpBinding, SettleUpViewModel>(R.layout.activity_settle_up) {

    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CurrencyUtil.currency = sharedPreferenceUtil.getString("symbol", "원")

        setShareSettlementInform()
        setUpBottomNavigation()
        setupJetpackNavigation()
        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 내역추가
            viewModel.clickedAddHistory.collect {
                startActivity(
                    Intent(
                        this@SettleUpActivity,
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

    private fun setupJetpackNavigation() {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = host.navController
        setUpShareDeepLink()
    }

    fun startSettleUpActivity() {
        startActivity(Intent(this, SettleUpActivity::class.java))
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        finishAffinity()
    }
    fun startHomeActivity() {
        startActivity(
            Intent(
                this@SettleUpActivity,
                HomeActivity::class.java
            ).putExtra("accessCheck", true)
        )
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun setUpBottomNavigation() {
        // 가운데 메뉴(제보하기)에 대한 터치 이벤트를 막기 위한 로직
        binding.bottomNavigationView.apply {
            menu.getItem(2).isEnabled = false
            selectedItemId = R.id.settleUpFragment
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
    private fun setUpShareDeepLink(){
        AppsFlyerLib.getInstance().init("${appsflyer_dev_key}", null, this)
        AppsFlyerLib.getInstance().start(this)

        AppsFlyerLib.getInstance().subscribeForDeepLink(object : DeepLinkListener {
            override fun onDeepLinking(deepLinkResult: DeepLinkResult) {
                when (deepLinkResult.status) {
                    DeepLinkResult.Status.FOUND -> {

                        if(sharedPreferenceUtil.getString("accessToken", "") == "") { // 로그인 상태 X

                            val intent = Intent(this@SettleUpActivity, LoginActivity::class.java)

                            startActivity(intent)
                            if (Build.VERSION.SDK_INT >= 34) {
                                overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
                            } else {
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                            }
                            finishAffinity()
                        }
                        else{
                            val deepLinkObj = deepLinkResult.deepLink

                            val settlementId = deepLinkObj.values["settlementId"].toString().toLong()
                            val bookKey = deepLinkObj.values["bookKey"].toString()

                            Timber.e("settlement ${settlementId} ------ ${bookKey}")
                            goShareSettlement(settlementId, bookKey)
                        }
                    }
                    DeepLinkResult.Status.NOT_FOUND -> {
                        Timber.d("deep Deep link not found")
                        return
                    }
                    else -> {
                        // dlStatus == DeepLinkResult.Status.ERROR
                        val dlError = deepLinkResult.error
                        Timber.d("deep There was an error getting Deep Link data: $dlError"
                        )
                        return
                    }
                }
                var deepLinkObj: DeepLink = deepLinkResult.deepLink
                try {
                    Timber.d("deep The DeepLink data is: $deepLinkObj"
                    )
                } catch (e: Exception) {
                    Timber.d("deep DeepLink data came back null"
                    )
                    return
                }

                // An example for using is_deferred
                if (deepLinkObj.isDeferred == true) {
                    Timber.d("deep This is a deferred deep link");
                } else {
                    Timber.d( "deep This is a direct deep link");
                }

                try {
                    val fruitName = deepLinkObj.deepLinkValue
                    Timber.d("deep The DeepLink will route to: $fruitName")
                } catch (e:Exception) {
                    Timber.d( "deep There's been an error: $e");
                    return;
                }
            }
        })
    }
    private fun setShareSettlementInform(){ // 딥 링크로 부터 받아온 값
        val settlementId = intent.getStringExtra("settlementId")?.toLong()
        val bookKey = intent.getStringExtra("bookKey")?:""
        goShareSettlement(settlementId, bookKey)
    }
    private fun goShareSettlement(settlementId: Long?, bookKey: String) {
        if (settlementId != null && bookKey.isNotEmpty()) {
            viewModel.settingBookKey(settlementId, bookKey)
        } else {
            Log.d("DeepLink", "SettleUp not found")
        }
    }

}