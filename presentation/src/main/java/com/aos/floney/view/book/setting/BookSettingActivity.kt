package com.aos.floney.view.book.setting

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityBookSettingBinding
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.book.setting.budget.BookSettingBudgetFragment
import com.aos.floney.view.book.setting.category.BookCategoryActivity
import com.aos.floney.view.book.setting.favorite.BookFavoriteActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.signup.SignUpCompleteActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookSettingActivity : BaseActivity<ActivityBookSettingBinding, BookSettingViewModel>(R.layout.activity_book_setting), BookSettingBudgetFragment.OnFragmentInteractionListener {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpAnimation()
        setupJetpackNavigation()
    }
    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(
                Activity.OVERRIDE_TRANSITION_CLOSE,
                R.anim.anim_slide_in_from_left_fade_in,
                android.R.anim.fade_out)
        } else {
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, android.R.anim.fade_out)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(
                Activity.OVERRIDE_TRANSITION_CLOSE,
                R.anim.anim_slide_in_from_left_fade_in,
                android.R.anim.fade_out)
        } else {
            overridePendingTransition(R.anim.anim_slide_in_from_left_fade_in, android.R.anim.fade_out)
        }
    }
    private fun setupJetpackNavigation() {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_book_setting_fragment_container) as NavHostFragment
        navController = host.navController

    }

    fun setUpAnimation(){
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.anim_slide_in_from_right_fade_in, android.R.anim.fade_out)
        } else {
            overridePendingTransition(R.anim.anim_slide_in_from_right_fade_in, android.R.anim.fade_out)
        }
    }

    // 홈 화면으로 이동
    fun startHomeActivity() {
        finish()
    }

    // 가계부 추가 생성 화면으로 이동

    fun startBookAddActivity() {
        startActivity(Intent(this, SignUpCompleteActivity::class.java))
        finishAffinity()
    }

    // 분류 항목 관리 화면으로 이동
    fun startBookCategoryActivity(){
        startActivity(Intent(this, BookCategoryActivity::class.java))
    }

    //즐겨찾기 설정 화면으로 이동
    fun startBookFavoriteActivity(){
        startActivity(Intent(this, BookFavoriteActivity::class.java))
    }


    override fun onFragmentRemoved() {

    }
}