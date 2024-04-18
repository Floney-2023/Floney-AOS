package com.aos.floney.view.book.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentBookSettingMainBinding
import com.aos.floney.databinding.FragmentMyPageInformEmailMainBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.mypage.inform.email.login.version.MyPageInformEmailActivity
import com.aos.floney.view.mypage.inform.withdraw.MyPageInformWithdrawInputPasswordFragmentDirections
import com.aos.floney.view.mypage.setting.main.MyPageSettingMainFragmentDirections
import com.aos.model.book.MyBookUsers
import com.aos.model.book.UiBookSettingModel
import com.aos.model.home.OurBookUsers
import com.aos.model.settlement.Details
import com.aos.model.settlement.UiSettlementAddModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class BookSettingMainFragment :
    BaseFragment<FragmentBookSettingMainBinding, BookSettingMainViewModel>(R.layout.fragment_book_setting_main), UiBookSettingModel.OnItemClickListener {
    override fun onItemClick(item: MyBookUsers) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@BookSettingMainFragment)
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    val activity = requireActivity() as BookSettingActivity
                    activity.startHomeActivity()
                }
            }
        }
        repeatOnStarted {
            viewModel.settingPage.collect() {
                if(it) {
                    val settingAction = BookSettingMainFragmentDirections.actionBookSettingMainFragmentToBookSettingEditFragment(
                        viewModel.bookSettingInfo.value!!.bookImg,
                        viewModel.bookSettingInfo.value!!.seeProfileStatus,
                        if (viewModel.bookSettingInfo.value!!.ourBookUsers[0].role.equals("방장·나")) true else false,
                        viewModel.bookSettingInfo.value!!.ourBookUsers.size
                    )
                    findNavController().navigate(settingAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.currencyPage.collect(){
                if(it){
                    val currencyAction = BookSettingMainFragmentDirections.actionBookSettingMainFragmentToBookSettingCurrencyFragment()
                    findNavController().navigate(currencyAction)
                }
            }
        }
    }
}