package com.aos.floney.view.history

import android.content.Context
import android.os.Build
import android.os.Bundle
import com.aos.floney.R
import com.aos.floney.databinding.BottomSheetCategoryBinding
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


class CategoryBottomSheetDialog(
    context: Context,
    private val dayViewDecorator: com.prolificinteractive.materialcalendarview.DayViewDecorator,
    private val clickedDate: (String) -> Unit,
    private val clickedChoiceBtn: () -> Unit,
) : BottomSheetDialog(context) {

    lateinit var binding: BottomSheetCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BottomSheetCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 선택 버튼 클릭 리스너
        binding.btnChoice.setOnClickListener {
            clickedChoiceBtn()
            this.dismiss()
        }
    }
}