package com.aos.floney.view.book.setting

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.databinding.ActivityBookSettingBinding
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.book.setting.category.BookCategoryActivity
import com.aos.floney.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookSettingActivity : BaseActivity<ActivityBookSettingBinding, BookSettingViewModel>(R.layout.activity_book_setting) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
    }

    private fun setupJetpackNavigation() {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_book_setting_fragment_container) as NavHostFragment
        navController = host.navController

    }

    // 홈 화면으로 이동
    fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }

    // 가계부 추가 생성 화면으로 이동

    fun startBookAddActivity() {
        startActivity(Intent(this, BookAddActivity::class.java))
        finishAffinity()
    }

    // 분류 항목 관리 화면으로 이동
    fun startBookCategoryActivity(){
        startActivity(Intent(this, BookCategoryActivity::class.java))
    }
}