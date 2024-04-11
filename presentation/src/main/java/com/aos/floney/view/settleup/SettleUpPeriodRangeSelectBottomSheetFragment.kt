package com.aos.floney.view.settleup

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import com.aos.floney.R
import com.aos.floney.base.BaseBottomSheetFragment
import com.aos.floney.databinding.BottomSheetMyPageBookAddSelectBinding
import com.aos.floney.databinding.BottomSheetSettleUpPeriodSelectBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.PeriodCalendar
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiPeriodSelectModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import androidx.databinding.library.baseAdapters.BR
@AndroidEntryPoint
class SettleUpPeriodRangeSelectBottomSheetFragment(private val onSelect: (Boolean) -> Unit) :
    BaseBottomSheetFragment<BottomSheetSettleUpPeriodSelectBinding, SettleUpPeriodRangeSelectViewModel>(R.layout.bottom_sheet_settle_up_period_select), UiPeriodSelectModel.OnItemClickListener {
    override fun onItemClick(item: PeriodCalendar) {

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }

    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@SettleUpPeriodRangeSelectBottomSheetFragment)
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