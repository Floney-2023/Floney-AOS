package com.aos.floney.view.analyze

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import com.aos.floney.databinding.BottomSheetDatePickerBinding
import com.aos.floney.view.book.setting.budget.BookSettingBudgetFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date

class ChoiceDatePickerBottomSheet(context: Context, private val todayText: String, private val onClickChoiceBtn: (String) -> Unit): BottomSheetDialog(context) {

    private val yearValues = ArrayList<String>()
    private val monthValues = ArrayList<String>()

    lateinit var binding: BottomSheetDatePickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BottomSheetDatePickerBinding.inflate(layoutInflater)
        binding.dialog = this
        setContentView(binding.root)

        setUpDateBottomSheetDialog()
    }

    private fun setUpDateBottomSheetDialog() {
        val calender = Calendar.getInstance()
        val todayDate = SimpleDateFormat("yyyy").format(calender.time).toInt()
        calender.time = java.util.Date(System.currentTimeMillis())
        binding.numberPickerYear.minValue = 0
        binding.numberPickerYear.maxValue = 20

        binding.numberPickerMonth.minValue = 1
        binding.numberPickerMonth.maxValue = 12

        // 년 세팅
        for (i in todayDate - 5..todayDate + 15) {
            yearValues.add("$i")
        }
        // 월 세팅
        for (i in 1..12) {
            monthValues.add("$i")
        }

        val year = todayText.split("-")[0].toInt()
        val month = todayText.split("-")[1].toInt()

        yearValues.forEachIndexed { index, s ->
            if(s.equals(year.toString())) {
                binding.numberPickerYear.value = index
            }
        }

        monthValues.forEachIndexed { index, s ->
            if(s.equals(month.toString())) {
                binding.numberPickerMonth.value = index + 1
            }
        }

        binding.numberPickerYear.displayedValues = yearValues.toTypedArray()
        binding.numberPickerMonth.displayedValues = monthValues.toTypedArray()
    }

    // 완료 버튼 클릭
    fun onClickChoiceBtn() {
        val year = yearValues[binding.numberPickerYear.value]
        // 10월 이하면 앞에 0 붙여줌
        val month = if (monthValues[binding.numberPickerMonth.value - 1].length < 3) {
            "0${monthValues[binding.numberPickerMonth.value - 1]}"
        } else {
            monthValues[binding.numberPickerMonth.value - 1]
        }

        onClickChoiceBtn("$year-$month-01")
        this.dismiss()
    }
}