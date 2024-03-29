package com.aos.floney.view.onboard

import android.os.Bundle
import android.view.View
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentOnBoardStartBinding

class OnBoardStartFragment : BaseFragment<FragmentOnBoardStartBinding, OnBoardStartViewModel>(R.layout.fragment_on_board_start) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        viewModel.onClickStartBtn.observe(viewLifecycleOwner) {
            val activity = requireActivity() as OnBoardActivity
            activity.startLoginActivity()
        }
    }
}