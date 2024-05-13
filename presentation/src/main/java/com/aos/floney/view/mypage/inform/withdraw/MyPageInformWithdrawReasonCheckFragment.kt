package com.aos.floney.view.mypage.inform.withdraw

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageInformProfilechangeBinding
import com.aos.floney.databinding.FragmentMyPageWithdrawReasonCheckBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.signup.SignUpInputEmailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyPageInformWithdrawReasonCheckFragment :
    BaseFragment<FragmentMyPageWithdrawReasonCheckBinding, MyPageInformWithdrawReasonCheckViewModel>(R.layout.fragment_my_page_withdraw_reason_check) {

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
            viewModel.nextPage.collect() {
                if(it.isNotEmpty()) {
                    val reasonType = it
                    val reason = viewModel.directInputText.value ?: ""
                    val withdrawPopupAction =
                        MyPageInformWithdrawReasonCheckFragmentDirections.actionMyPageInformWithdrawReasonCheckFragmentToMyPageInformWithdrawInputPasswordFragment(
                            reasonType,
                            reason
                        )
                    findNavController().navigate(withdrawPopupAction)
                }
            }
        }
    }
}