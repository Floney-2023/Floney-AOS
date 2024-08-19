package com.aos.floney.view.book.setting.currency

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.base.BaseViewModel
import com.aos.floney.databinding.FragmentBookSettingCurrencyBinding
import com.aos.floney.ext.parseErrorMsg
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.book.setting.BookSettingActivity
import com.aos.floney.view.common.WarningPopupDialog
import com.aos.model.book.Currency
import com.aos.model.book.UiBookCurrencyModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookSettingCurrencyFragment : BaseFragment<FragmentBookSettingCurrencyBinding, BookSettingCurrencyViewModel>(R.layout.fragment_book_setting_currency) , UiBookCurrencyModel.OnItemClickListener {

    override fun onItemClick(item: Currency) {

        // 화폐 단위를 문자열로 조합
        val currencyString = "${item.code}(${item.symbol})"

        // 문자열 리소스에 동적으로 값을 삽입
        val dialogInfo = getString(R.string.book_setting_currency_dialog_info, currencyString)

        val exitDialogFragment = WarningPopupDialog(
            getString(R.string.book_setting_currency_dialog_title),
            dialogInfo,
            getString(R.string.book_setting_currency_dialog_left_button),
            getString(R.string.book_setting_currency_dialog_right_button),
            false
        ) { checked ->
            if (checked)
                viewModel.settingCurrency(item)
        }
        exitDialogFragment.show(parentFragmentManager, "initDialog")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@BookSettingCurrencyFragment)
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
        repeatOnStarted {
            // 초기화 후, 이전 페이지 (가계부 설정) 으로
            viewModel.init.collect {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }

    }

}