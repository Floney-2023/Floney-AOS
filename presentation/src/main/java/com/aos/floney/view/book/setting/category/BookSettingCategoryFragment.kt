package com.aos.floney.view.book.setting.category

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentBookSettingCategoryBinding
import com.aos.floney.databinding.FragmentBookSettingCurrencyBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.setting.BookSettingActivity
import com.aos.floney.view.book.setting.currency.BookSettingCurrencyViewModel
import com.aos.floney.view.common.WarningPopupDialog
import com.aos.model.book.Currency
import com.aos.model.book.UiBookCategory
import com.aos.model.book.UiBookCurrencyModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookSettingCategoryFragment : BaseFragment<FragmentBookSettingCategoryBinding, BookSettingCategoryViewModel>(R.layout.fragment_book_setting_category) , UiBookCategory.OnItemClickListener {

    override fun onItemClick(item: UiBookCategory) {

        val dialogInfo = getString(R.string.book_setting_category_dialog_info, item.name)

        val exitDialogFragment = WarningPopupDialog(
            getString(R.string.book_setting_category_dialog_title),
            dialogInfo,
            getString(R.string.book_setting_category_dialog_left_button),
            getString(R.string.book_setting_category_dialog_right_button)
        ) { checked ->
            if (checked)
                viewModel.deleteCategory(item)
        }
        exitDialogFragment.show(parentFragmentManager, "initDialog")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@BookSettingCategoryFragment)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 이전 페이지로
            viewModel.back.collect {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }

    }

}