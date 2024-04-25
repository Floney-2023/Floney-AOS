package com.aos.floney.view.book.add

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSignUpCompleteBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpCompleteFragment : BaseFragment<FragmentSignUpCompleteBinding, SignUpCompleteViewModel>(R.layout.fragment_sign_up_complete) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.nextPage.collect() {
                if(it) {
                    val action =
                        SignUpCompleteFragmentDirections.actionSignUpCompleteFragmentToBookAddInviteCheckFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}