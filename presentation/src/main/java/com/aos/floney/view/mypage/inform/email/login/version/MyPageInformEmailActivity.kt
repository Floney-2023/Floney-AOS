package com.aos.floney.view.mypage.inform.email.login.version

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityMyPageInformEmailBinding
import com.aos.floney.view.mypage.MyPageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageInformEmailActivity :
    BaseActivity<ActivityMyPageInformEmailBinding, MyPageInformEmailViewModel>(R.layout.activity_my_page_inform_email) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
    }

    private fun setupJetpackNavigation() {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = host.navController

    }
    fun startMyPageActivity(){
        startActivity(Intent(this, MyPageActivity::class.java))
        finishAffinity()
    }
}