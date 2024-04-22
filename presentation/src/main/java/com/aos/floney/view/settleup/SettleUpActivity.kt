package com.aos.floney.view.settleup

import android.content.Intent
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivitySettleUpBinding
import com.aos.floney.databinding.ActivitySignUpBinding
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.login.LoginActivity
import com.aos.floney.view.mypage.MyPageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettleUpActivity : BaseActivity<ActivitySettleUpBinding, SettleUpViewModel>(R.layout.activity_settle_up) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
        setUpBottomNavigation()
    }
    private fun setupJetpackNavigation() {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = host.navController

    }

    // 회원가입 완료 후 로그인 페이지로 이동
    fun startSettleUpActivity() {
        startActivity(Intent(this, SettleUpActivity::class.java))
        finishAffinity()
    }

    fun startBookAddActivity() {
        startActivity(Intent(this, BookAddActivity::class.java))
        finishAffinity()
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
                    finish()
                    false
                }
                R.id.analysisFragment -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    false
                }
                R.id.mypageFragment -> {
                    startActivity(Intent(this, MyPageActivity::class.java))
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