package com.aos.floney.view.mypage.inform.withdraw

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageWithdrawCompleteBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.mypage.inform.MyPageInformActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageInformWithdrawCompleteFragment :
    BaseFragment<FragmentMyPageWithdrawCompleteBinding, MyPageInformWithdrawCompleteViewModel>(R.layout.fragment_my_page_withdraw_complete) {

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
                if(it) {
                    val activity = requireActivity() as MyPageInformActivity
                    activity.startLoginActivity()
                }
            }
        }
    }
}