package com.aos.floney.view.mypage.setting.alarm

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageSettingAlarmBinding
import com.aos.floney.ext.repeatOnStarted
import com.suke.widget.SwitchButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageSettingAlarmFragment :
    BaseFragment<FragmentMyPageSettingAlarmBinding, MyPageSettingAlarmViewModel>(R.layout.fragment_my_page_setting_alarm) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
        setUpToggleListener()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }
    }
    private fun setUpToggleListener(){
        binding.switchButton.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                viewModel.onClickMarketingTerms()
                true
            } else false
        }
    }
}