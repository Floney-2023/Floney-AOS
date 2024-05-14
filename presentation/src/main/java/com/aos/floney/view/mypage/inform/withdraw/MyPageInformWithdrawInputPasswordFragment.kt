package com.aos.floney.view.mypage.inform.withdraw

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.base.BaseViewModel
import com.aos.floney.databinding.FragmentMyPageInformProfilechangeBinding
import com.aos.floney.databinding.FragmentMyPageWithdrawInputPasswordBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.common.WarningPopupDialog
import com.aos.floney.view.signup.SignUpInputEmailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyPageInformWithdrawInputPasswordFragment :
    BaseFragment<FragmentMyPageWithdrawInputPasswordBinding, MyPageInformWithdrawInputPasswordViewModel>(R.layout.fragment_my_page_withdraw_input_password) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }
        repeatOnStarted {
            viewModel.dialogPage.collect() {
                if(it) {
                    val exitDialogFragment = WarningPopupDialog(
                        getString(R.string.mypage_main_inform_exit_popup_title),
                        getString(R.string.mypage_main_inform_exit_popup_info),
                        getString(R.string.mypage_main_inform_exit_popup_btn_exit),
                        getString(R.string.mypage_main_inform_exit_popup_btn_cancel),
                        false
                    ) { checked ->
                        if (checked) {
                            viewModel.requestWithdraw()
                        }
                    }
                    exitDialogFragment.show(parentFragmentManager, "WithdrawDialog")
                }
            }
        }
        repeatOnStarted {
            viewModel.withdrawPage.collect() {
                if(it) {
                    val action =
                        MyPageInformWithdrawInputPasswordFragmentDirections.actionMyPageInformWithdrawInputPasswordFragmentToMyPageInformWithdrawCompleteFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}