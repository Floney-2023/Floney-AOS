package com.aos.floney.view.book.setting.favorite

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityBookCategoryBinding
import com.aos.floney.databinding.ActivityBookFavoriteBinding
import com.aos.floney.view.book.setting.BookSettingActivity
import com.aos.floney.view.book.setting.category.BookCategoryActivity
import com.aos.floney.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFavoriteActivity : BaseActivity<ActivityBookFavoriteBinding, BookFavoriteViewModel>(R.layout.activity_book_favorite) {
    private lateinit var navController: NavController
    private var entryPoint = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
        entryPoint = intent.getStringExtra("entryPoint").toString()
    }

    private fun setupJetpackNavigation() {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_book_category_favorite_container) as NavHostFragment
        navController = host.navController

    }

    // 홈 화면으로 이동
    fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }

    // 카테고리 설정 화면으로 이동
    fun startBookCategoryActivity() {
        startActivity(Intent(this, BookCategoryActivity::class.java))
    }

    // 가계부 설정 화면으로 이동
    fun startBookSettingActivity() {
        if(entryPoint != "") {
            finish()
        } else {
            startActivity(Intent(this, BookSettingActivity::class.java).putExtra("entryPoint", "history"))
            finishAffinity()
        }
    }
}