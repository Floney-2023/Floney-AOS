package com.aos.floney.view.history

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityHistoryBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.model.book.UiBookCategory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HistoryActivity :
    BaseActivity<ActivityHistoryBinding, HistoryViewModel>(R.layout.activity_history), UiBookCategory.OnItemClickListener {
    private lateinit var calendarBottomSheetDialog: CalendarBottomSheetDialog
    private lateinit var categoryBottomSheetDialog: CategoryBottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModelObserver()
        setUpCalendarBottomSheet()
        setUpCategoryBottomSheet()
    }

    private fun setUpCalendarBottomSheet() {
        calendarBottomSheetDialog = CalendarBottomSheetDialog(this@HistoryActivity, DayDisableDecorator(this@HistoryActivity), {date ->
            viewModel.setCalendarDate(date)
        }, {
            viewModel.onClickCalendarChoice()
        })
    }

    private fun setUpCategoryBottomSheet() {
        categoryBottomSheetDialog = CategoryBottomSheetDialog(this@HistoryActivity, viewModel, this@HistoryActivity) {
            viewModel.onClickCategoryChoiceDate()
        }
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.showCalendar.collect {
                if(it) {
                    if(!calendarBottomSheetDialog.isShowing) {
                        calendarBottomSheetDialog.show()
                    }
                }
            }
        }
        repeatOnStarted {
            viewModel.onClickCategory.collect {
                if(it) {
                    categoryBottomSheetDialog.show()
                }
            }
        }
    }

    inner class DayDisableDecorator(context: Context) : DayViewDecorator {
        private val drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector)
        private val unselectedTextColor = ContextCompat.getColor(context, R.color.grayscale2)
        private val selectedTextColor = ContextCompat.getColor(context, R.color.white)
        override fun shouldDecorate(day: CalendarDay): Boolean {
            // 휴무일 || 이전 날짜
            return true
        }

        override fun decorate(view: DayViewFacade) {
            view.let {
                if (drawable != null) {
                    it.setSelectionDrawable(drawable)
                }
            }
        }
    }

    override fun onItemClick(item: UiBookCategory) {
        Timber.e("item $item")
    }
}