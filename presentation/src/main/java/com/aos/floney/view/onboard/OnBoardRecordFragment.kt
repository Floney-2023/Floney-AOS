package com.aos.floney.view.onboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentOnBoardRecordBinding
import kotlinx.coroutines.launch

class OnBoardRecordFragment :
    BaseFragment<FragmentOnBoardRecordBinding, OnBoardRecordViewModel>(R.layout.fragment_on_board_record) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        viewModel.onClickSkipBtn.observe(viewLifecycleOwner) {
            val activity = requireActivity() as OnBoardActivity
            activity.onChangeViewPagerItem(2)
        }
    }

}