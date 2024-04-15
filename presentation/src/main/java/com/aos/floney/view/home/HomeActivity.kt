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
import com.aos.model.home.DayMoneyModifyItem
import com.aos.model.home.MonthMoney
import com.aos.model.home.UiBookDayModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home),
    UiBookDayModel.OnItemClickListener {
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
            when (showType) {
                "month" -> {
                    viewModel.initCalendarMonth()
                    fragmentManager.beginTransaction()
                        .replace(R.id.fl_container, HomeMonthTypeFragment()).commit()
                }

                "day" -> {
                    viewModel.initCalendarDay()
                    fragmentManager.beginTransaction()
                        .replace(R.id.fl_container, HomeDayTypeFragment()).commit()
                }
            }
        }

        repeatOnStarted {
            // 내역추가
            viewModel.clickedAddHistory.collect {
                startActivity(
                    Intent(
                        this@HomeActivity,
                        HistoryActivity::class.java
                    ).putExtra("date", viewModel.getClickDate())
                        .putExtra("nickname", viewModel.getMyNickname())
                )
            }
        }
    }

    // 캘린더 아이템이 표시됨
    fun onClickCalendarItem(item: MonthMoney) {
        viewModel.onClickShowDetail(item)
    }

    override fun onItemClick(item: DayMoney) {
        startActivity(
            Intent(
                this@HomeActivity,
                HistoryActivity::class.java
            ).putExtra("dayItem", DayMoneyModifyItem(
                id = item.id,
                money = item.money,
                description = item.description,
                lineDate = viewModel.getClickDate(),
                lineCategory = item.lineCategory,
                lineSubCategory = item.lineSubCategory,
                assetSubCategory = item.assetSubCategory,
                exceptStatus = item.exceptStatus,
                writerNickName = item.writerNickName
            ))
        )
    }
}