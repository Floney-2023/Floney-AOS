package com.aos.floney.view.mypage.inform.simple.login.version

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityMyPageInformEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageInformSimpleActivity : BaseActivity<ActivityMyPageInformEmailBinding, MyPageInformSimpleViewModel>(R.layout.activity_my_page_setting) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupJetpackNavigation()
    }

    private fun setupJetpackNavigation() {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = host.navController

    }
}