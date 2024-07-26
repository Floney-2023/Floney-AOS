package com.aos.floney.view.password.find

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
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import java.util.Calendar

@AndroidEntryPoint
class SendPasswordBottomSheetFragment(private val onSelect: () -> Unit) :
    BaseBottomSheetFragment<BottomSheetSettleUpPeriodSelectBinding, SendPasswordBottomSheetViewModel>(R.layout.bottom_sheet_send_password) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 선택하기 buttonClick
            viewModel.onClickedLoginBtn.collect {
                onSelect()
                dismiss()
            }
        }
    }
}