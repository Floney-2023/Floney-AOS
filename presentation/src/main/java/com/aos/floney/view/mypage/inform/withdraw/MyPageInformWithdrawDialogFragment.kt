package com.aos.floney.view.mypage.inform.withdraw

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseDialogFragment
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageInformProfilechangeBinding
import com.aos.floney.databinding.FragmentMyPageWithdrawCompleteBinding
import com.aos.floney.databinding.FragmentMyPageWithdrawDialogBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.mypage.inform.email.login.version.MyPageInformEmailActivity
import com.aos.floney.view.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageInformWithdrawDialogFragment(private val onSelect: (Boolean) -> Unit) :
    BaseDialogFragment<FragmentMyPageWithdrawDialogBinding, MyPageInformWithdrawDialogViewModel>(R.layout.fragment_my_page_withdraw_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    onSelect(false)
                    dismiss()
                }
            }
        }
        repeatOnStarted {
            viewModel.nextPage.collect() {
                if(it) {
                    onSelect(true)
                    dismiss()
                }
            }
        }
    }
}