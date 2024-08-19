package com.aos.floney.view.book.setting.budget

import android.os.Bundle
import android.view.View
import com.aos.floney.R
import com.aos.floney.base.BaseBottomSheetFragment
import com.aos.floney.databinding.BottomSheetBookSettingBudgetBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.model.book.BudgetItem
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookSettingBudgetBottomSheetFragment(
    private val onClickBudgetItem : BudgetItem,
    private val dateString : String,
    private val onClickedChoiceBtn: (String) -> Unit
) :
    BaseBottomSheetFragment<BottomSheetBookSettingBudgetBinding, BookSettingBudgetBottomSheetViewModel>
        (R.layout.bottom_sheet_book_setting_budget) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()

    }
    private fun setUpUi()
    {
        Timber.e("checkcheck ${dateString}")
        viewModel.settingUi(onClickBudgetItem, dateString)
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.budgetSheet.collect {
                onClickedChoiceBtn(it)
                dismiss()
            }
        }
    }
}