package com.aos.floney.view.history

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityHistoryBinding
import com.aos.floney.ext.repeatOnStarted
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryActivity :
    BaseActivity<ActivityHistoryBinding, HistoryViewModel>(R.layout.activity_history) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.showCalendar.collect {
                val calendarBottomSheetDialog = CalendarBottomSheetDialog(this@HistoryActivity)
                calendarBottomSheetDialog.show()
            }
        }
    }

}