package com.aos.floney.view.home

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.library.baseAdapters.BR
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityHomeBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.history.HistoryActivity
import com.aos.model.home.DayMoney
import com.aos.model.home.MonthMoney
import com.aos.model.home.UiBookDayModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home), UiBookDayModel.OnItemClickListener {
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }

    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this)
    }

    private fun setUpViewModelObserver() {
        viewModel.clickedShowType.observe(this) { showType ->
            when(showType) {
                "month" -> {
                    viewModel.initCalendarMonth()
                    fragmentManager.beginTransaction().replace(R.id.fl_container, HomeMonthTypeFragment()).commit()
                }
                "day" -> {
                    viewModel.initCalendarDay()
                    fragmentManager.beginTransaction().replace(R.id.fl_container, HomeDayTypeFragment()).commit()
                }
            }
        }

        repeatOnStarted {
            viewModel.clickedAddHistory.collect {
                startActivity(Intent(this@HomeActivity, HistoryActivity::class.java))
            }
        }
    }

    // 캘린더 아이템이 표시됨
    fun onClickCalendarItem(item: MonthMoney) {
        viewModel.onClickShowDetail(item)
    }

    override fun onItemClick(item: DayMoney) {
        Timber.e("item $item")
    }
}