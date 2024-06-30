package com.aos.floney.view.onboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentOnBoardRecordBinding
import com.aos.floney.databinding.FragmentOnBoardShowFlowBinding

class OnBoardShowFlowFragment : BaseFragment<FragmentOnBoardShowFlowBinding, OnBoardShowFlowViewModel>(R.layout.fragment_on_board_show_flow) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        viewModel.onClickSkipBtn.observe(viewLifecycleOwner) {
            val activity = requireActivity() as OnBoardActivity
            activity.startLoginActivity()
        }
    }
}