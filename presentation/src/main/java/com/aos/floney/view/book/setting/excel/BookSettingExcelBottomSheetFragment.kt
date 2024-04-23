package com.aos.floney.view.book.setting.excel

import android.os.Bundle
import android.view.View
import com.aos.floney.R
import com.aos.floney.base.BaseBottomSheetFragment
import com.aos.floney.databinding.BottomSheetBookSettingAssetBinding
import com.aos.floney.databinding.BottomSheetBookSettingExcelBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookSettingExcelBottomSheetFragment :
    BaseBottomSheetFragment<BottomSheetBookSettingExcelBinding, BookSettingExcelViewModel>
        (R.layout.bottom_sheet_book_setting_excel) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModelObserver()

    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 나중에 하기 -> Bottomsheet 닫기
            viewModel.completePage.collect {
                Timber.e("nextPage $it")
                if(it!="") {
                    // 엑셀 파일 공유 코드
                    dismiss()
                }
            }
        }
    }
}