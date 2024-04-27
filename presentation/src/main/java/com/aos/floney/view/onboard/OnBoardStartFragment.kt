package com.aos.floney.view.onboard

import android.os.Bundle
import android.view.View
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentOnBoardStartBinding
import com.aos.data.util.SharedPreferenceUtil
import javax.inject.Inject

class OnBoardStartFragment : BaseFragment<FragmentOnBoardStartBinding, OnBoardStartViewModel>(R.layout.fragment_on_board_start) {
    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        viewModel.onClickStartBtn.observe(viewLifecycleOwner) {
            sharedPreferenceUtil.setBoolean(getString(R.string.is_first), false)

            val activity = requireActivity() as OnBoardActivity
            activity.startLoginActivity()
        }
    }
}