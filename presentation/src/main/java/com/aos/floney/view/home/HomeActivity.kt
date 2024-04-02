package com.aos.floney.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpFragment()
    }

    private fun setUpFragment() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fl_container, HomeCalendarTypeFragment()).commit()
    }
}