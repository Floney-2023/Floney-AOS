package com.aos.floney.view.analyze

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentBookSettingBudgetBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.model.book.BudgetItem
import com.aos.model.book.UiBookBudgetModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyzeSettingBudgetFragment : BaseFragment<FragmentBookSettingBudgetBinding, AnalyzeSettingBudgetViewModel>(R.layout.fragment_book_setting_budget) , UiBookBudgetModel.OnItemClickListener {

    override fun onItemClick(item: BudgetItem) {
        viewModel.settingBudget(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@AnalyzeSettingBudgetFragment)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 이전 페이지 이동
            viewModel.back.collect {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }

        repeatOnStarted {
            // 예산 설정 bottomSheet
            viewModel.budgetSettingPage.collect {
                if(it.month!="") {
                    val bottomSheetFragment = AnalyzeSettingBudgetBottomSheetFragment(
                        it, viewModel.convertToDateString(it.month)) { updateBudgetMoney ->
                        viewModel.updateBudget(it, updateBudgetMoney)
                    }
                    bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
                }
            }
        }
    }
}