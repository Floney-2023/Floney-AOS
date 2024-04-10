package com.aos.floney.view.history

import android.os.Bundle
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.databinding.ActivityHistoryBinding
import com.aos.floney.ext.repeatOnStarted
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryActivity : BaseActivity<ActivityHistoryBinding, HistoryViewModel>(R.layout.activity_history) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.showCalendar.collect {
                val bottomSheetDialog = BottomSheetDialog(this@HistoryActivity, R.style.CustomBottomSheetDialogTheme)
                // 레이아웃 파일 설정
                bottomSheetDialog.setContentView(R.layout.dialog_calendar)
                // Bottom Sheet Dialog 보여주기
                bottomSheetDialog.show()
            }
        }
    }

}