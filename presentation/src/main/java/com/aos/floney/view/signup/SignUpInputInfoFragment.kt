package com.aos.floney.view.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSignUpInputInfoBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpInputInfoFragment : BaseFragment<FragmentSignUpInputInfoBinding, SignUpInputInfoViewModel>(R.layout.fragment_sign_up_input_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.nextPage.collect() {
                if (it) {
                    val action =
                        SignUpInputInfoFragmentDirections.actionSignUpInputInfoFragmentToSignUpCompleteFragment()
                    findNavController().navigate(action)
                }
            }
        }

        repeatOnStarted {
            // 이전 페이지 이동
            viewModel.back.collect {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }
    }
}