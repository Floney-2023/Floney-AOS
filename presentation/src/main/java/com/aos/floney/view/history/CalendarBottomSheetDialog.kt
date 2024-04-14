package com.aos.floney.view.history

import android.content.Context
import android.os.Build
import android.os.Bundle
import com.aos.floney.R
import com.aos.floney.databinding.BottomSheetCalendarBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.logging.SimpleFormatter


class CalendarBottomSheetDialog(
    context: Context,
    private val clickedDate: String,
    private val dayViewDecorator: com.prolificinteractive.materialcalendarview.DayViewDecorator,
    private val onClickedDate: (String) -> Unit,
    private val onClickedChoiceBtn: () -> Unit,
) : BottomSheetDialog(context) {

    lateinit var binding: BottomSheetCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BottomSheetCalendarBinding.inflate(layoutInflater)
        binding.dialog = this
        setContentView(binding.root)

        setUpCalendarView()
    }

    fun onClickBtnChoice() {
        // 선택 버튼 클릭 리스너
        binding.btnChoice.setOnClickListener {
            onClickedChoiceBtn()
            this.dismiss()
        }
    }

    private fun setUpCalendarView() {
        binding.calendar.apply {
            addDecorator(dayViewDecorator)
            setWeekDayFormatter(ArrayWeekDayFormatter(context.resources.getTextArray(R.array.custom_weekdays)))
            setTitleFormatter(MyTitleFormatter())

            val date = clickedDate.split(".")
            currentDate = CalendarDay.from(date[0].toInt(), date[1].toInt(), date[2].toInt())
            selectedDate = CalendarDay.from(date[0].toInt(), date[1].toInt(), date[2].toInt())
        }

        binding.calendar.setOnDateChangedListener { widget, date, selected ->
            onClickedDate(date.toString().replace("CalendarDay",""))
        }
    }

    inner class MyTitleFormatter : TitleFormatter {
        override fun format(day: CalendarDay): CharSequence {
            return "${day.year}.${
                if (day.month < 10) {
                    "0${day.month}"
                } else {
                    day.month
                }
            }"
        }

    }
}