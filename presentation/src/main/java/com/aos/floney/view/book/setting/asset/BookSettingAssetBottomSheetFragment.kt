package com.aos.floney.view.book.setting.asset

import android.os.Bundle
import android.view.View
import com.aos.floney.R
import com.aos.floney.base.BaseBottomSheetFragment
import com.aos.floney.databinding.BottomSheetBookSettingAssetBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookSettingAssetBottomSheetFragment :
    BaseBottomSheetFragment<BottomSheetBookSettingAssetBinding, BookSettingAssetViewModel>
        (R.layout.bottom_sheet_book_setting_asset) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModelObserver()

    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 나중에 하기 -> Bottomsheet 닫기
            viewModel.initAssetSheet.collect {
                Timber.e("nextPage $it")
                if(it) {
                    dismiss()
                }
            }
        }
    }
}