package com.aos.floney.view.book.setting.carryinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.aos.floney.R
import com.aos.floney.base.BaseBottomSheetFragment
import com.aos.floney.databinding.BottomSheetBookSettingCarryinfoBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.setting.BookSettingMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookSettingCarryInfoSheetFragment(
    private val carryOver: Boolean,
    private val onClickedChoiceBtn: (Boolean) -> Unit
    ) :
    BaseBottomSheetFragment<BottomSheetBookSettingCarryinfoBinding, BookSettingCarryinfoViewModel>
        (R.layout.bottom_sheet_book_setting_carryinfo) {

    private val activityViewModel: BookSettingMainViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCarryOver()
        setUpViewModelObserver()
    }
    private fun setCarryOver(){
        viewModel.setCarryInfo(carryOver)
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 나중에 하기 -> Bottomsheet 닫기
            viewModel.carryOverSheet.collect {
                Timber.e("nextPage $it")
                if(it) {
                    onClickedChoiceBtn(viewModel.carryInfo.value!!)
                    dismiss()
                }
            }
        }
    }
}