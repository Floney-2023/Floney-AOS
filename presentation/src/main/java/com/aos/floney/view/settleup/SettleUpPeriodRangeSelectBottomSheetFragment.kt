package com.aos.floney.view.settleup

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.aos.floney.R
import com.aos.floney.base.BaseBottomSheetFragment
import com.aos.floney.databinding.BottomSheetSettleUpPeriodSelectBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.model.settlement.PeriodCalendar
import com.aos.model.settlement.UiPeriodSelectModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import java.util.Calendar

@AndroidEntryPoint
class SettleUpPeriodRangeSelectBottomSheetFragment(private val onSelect: (String, String, String?) -> Unit) :
    BaseBottomSheetFragment<BottomSheetSettleUpPeriodSelectBinding, SettleUpPeriodRangeSelectViewModel>(R.layout.bottom_sheet_settle_up_period_select), UiPeriodSelectModel.OnItemClickListener {
    override fun onItemClick(item: PeriodCalendar) {
        viewModel.updateAdjustPeriod(item)
    }

    override fun onStart() {
        super.onStart()

        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }

    private fun setUpUi() {
        binding.rvCalendar.itemAnimator = null
        binding.setVariable(BR.eventHolder, this@SettleUpPeriodRangeSelectBottomSheetFragment)
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 선택하기 buttonClick
            viewModel.selectButton.collect {
                Timber.e("nextPage $it")
                if(it != "") {
                    onSelect(viewModel.startDateFormat.value!!, viewModel.endDateFormat.value!!, it)
                    dismiss()
                }
                else{
                    dismiss()
                }
            }
        }
    }
}