package com.aos.floney.view.mypage.bookadd

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import com.aos.floney.R
import com.aos.floney.base.BaseBottomSheetFragment
import com.aos.floney.databinding.BottomSheetMyPageBookAddSelectBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MypageBookAddSelectBottomSheetFragment(private val onSelect: (Boolean) -> Unit) :
    BaseBottomSheetFragment<BottomSheetMyPageBookAddSelectBinding, MypageBookAddSelectViewModel>
        (R.layout.bottom_sheet_my_page_book_add_select) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 추가하기 buttonClick
            viewModel.addButton.collect {
                Timber.e("nextPage $it")
                if(it) {
                    onSelect(viewModel.bookCreateTerms.value!!)
                }
            }
        }
    }
}