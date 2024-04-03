package com.aos.floney.view.book.add

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookAddActivity : BaseActivity<ActivityBookAddBinding, BookAddViewModel>(R.layout.activity_book_add) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
    }

    private fun setupJetpackNavigation() {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_book_add_fragment_container) as NavHostFragment
        navController = host.navController

    }

    // 가계부 초대되거나, 생성 완료 후, 홈 화면으로 이동
    fun startHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
    }
}