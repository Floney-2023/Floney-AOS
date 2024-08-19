package com.aos.floney.view.book.setting.budget

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import com.aos.floney.databinding.BottomSheetDatePickerBinding
import com.aos.floney.databinding.BottomSheetYearPickerBinding
import com.aos.floney.view.book.setting.budget.BookSettingBudgetFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date

class ChoiceYearPickerBottomSheet(context: Context, private val todayYear: String, private val onClickChoiceBtn: (String) -> Unit): BottomSheetDialog(context) {

    private val yearValues = ArrayList<String>()

    lateinit var binding: BottomSheetYearPickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BottomSheetYearPickerBinding.inflate(layoutInflater)
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

        // 년 세팅
        for (i in todayDate - 5..todayDate + 15) {
            yearValues.add("$i")
        }

        yearValues.forEachIndexed { index, s ->
            if(s.equals(todayYear)) {
                binding.numberPickerYear.value = index
            }
        }
        binding.numberPickerYear.displayedValues = yearValues.toTypedArray()
    }

    // 완료 버튼 클릭
    fun onClickChoiceBtn() {
        val year = yearValues[binding.numberPickerYear.value]

        onClickChoiceBtn("$year-01-01")
        this.dismiss()
    }
}