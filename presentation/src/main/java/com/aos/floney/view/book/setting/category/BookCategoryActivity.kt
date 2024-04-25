package com.aos.floney.view.book.setting.category

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.databinding.ActivityBookCategoryBinding
import com.aos.floney.databinding.ActivityBookSettingBinding
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.book.setting.BookSettingActivity
import com.aos.floney.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookCategoryActivity : BaseActivity<ActivityBookCategoryBinding, BookCategoryViewModel>(R.layout.activity_book_category) {
    private lateinit var navController: NavController
    private var entryPoint = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
        entryPoint = intent.getStringExtra("entryPoint").toString()
    }

    private fun setupJetpackNavigation() {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_book_category_fragment_container) as NavHostFragment
        navController = host.navController

    }

    // 홈 화면으로 이동
    fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }

    // 가계부 설정 화면으로 이동
    fun startBookSettingActivity() {
        if(entryPoint != "") {
            finish()
        } else {
            startActivity(Intent(this, BookSettingActivity::class.java))
            finishAffinity()
        }
    }
}