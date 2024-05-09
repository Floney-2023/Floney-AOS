package com.aos.floney.view.book.entrance

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityBookSettingBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.add.BookAddActivity
import com.aos.floney.view.book.setting.budget.BookSettingBudgetFragment
import com.aos.floney.view.book.setting.category.BookCategoryActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.password.find.PasswordFindActivity
import com.aos.floney.view.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookEntranceActivity : BaseActivity<ActivityBookSettingBinding, BookEntranceViewModel>(R.layout.activity_book_setting) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {

    }
}