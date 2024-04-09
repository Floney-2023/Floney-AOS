package com.aos.floney.view.mypage.bookadd.codeinput

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityMyPageBookCodeInputBinding
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.mypage.MyPageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageBookCodeInputActivity :
    BaseActivity<ActivityMyPageBookCodeInputBinding, MyPageBookCodeInputViewModel>(R.layout.activity_my_page_book_code_input) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
    }

    private fun setupJetpackNavigation() {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_mypage_book_code_input_container) as NavHostFragment
        navController = host.navController

    }
    // 가계부 초대된 후, 홈 화면으로 이동
    fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }
    fun startMyPageActivity() {
        startActivity(Intent(this, MyPageActivity::class.java))
        finishAffinity()
    }
}