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
    private val dayViewDecorator: com.prolificinteractive.materialcalendarview.DayViewDecorator,
    private val clickedDate: (String) -> Unit,
    private val clickedChoiceBtn: () -> Unit,
) : BottomSheetDialog(context) {

    lateinit var binding: BottomSheetCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BottomSheetCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpCalendarView()

        // 선택 버튼 클릭 리스너
        binding.btnChoice.setOnClickListener {
            clickedChoiceBtn()
            this.dismiss()
        }
    }

    private fun setUpCalendarView() {
        binding.calendar.apply {
            addDecorator(dayViewDecorator)
            setWeekDayFormatter(ArrayWeekDayFormatter(context.resources.getTextArray(R.array.custom_weekdays)))
            setTitleFormatter(MyTitleFormatter())
            val date = CalendarDay.today().date.apply {
                withYear(2024)
                withMonth(4)
                withDayOfMonth(15)
            }
            currentDate = CalendarDay.from(2024, 12, 25)
            selectedDate = CalendarDay.from(2024, 12, 25)
        }

        binding.calendar.setOnDateChangedListener { widget, date, selected ->
            clickedDate(date.toString().replace("CalendarDay",""))
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