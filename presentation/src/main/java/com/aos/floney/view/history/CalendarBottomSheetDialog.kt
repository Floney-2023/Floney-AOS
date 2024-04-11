package com.aos.floney.view.history

import android.content.Context
import android.os.Bundle
import com.aos.floney.R
import com.aos.floney.databinding.DialogCalendarBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class CalendarBottomSheetDialog(context: Context): BottomSheetDialog(context, R.style.CustomBottomSheetDialogTheme) {

    lateinit var binding: DialogCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}