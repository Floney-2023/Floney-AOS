package com.aos.floney.view.book.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentBookSettingCurrencyBinding
import com.aos.floney.databinding.FragmentSettleUpMemberSelectBinding
import com.aos.floney.databinding.FragmentSettleUpStartBinding
import com.aos.floney.databinding.FragmentSignUpAgreeBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.signup.SignUpActivity
import com.aos.model.book.Currency
import com.aos.model.book.UiBookCurrencyModel
import com.aos.model.home.MonthMoney
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.user.MyBooks
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class BookSettingCurrencyFragment : BaseFragment<FragmentBookSettingCurrencyBinding, BookSettingCurrencyViewModel>(R.layout.fragment_book_setting_currency) , UiBookCurrencyModel.OnItemClickListener {

    override fun onItemClick(item: Currency) {
        viewModel.settingCurrency(item)
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

    }

}