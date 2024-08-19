package com.aos.floney.view.mypage.inform.pwchange

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageInformPwchangeBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageInformPwChangeFragment : BaseFragment<FragmentMyPageInformPwchangeBinding, MyPageInformPwChangeViewModel>(R.layout.fragment_my_page_inform_pwchange) {

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
            viewModel.checkBtn.collect() {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }
    }
}