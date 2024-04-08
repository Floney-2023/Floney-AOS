package com.aos.floney.view.mypage.setting.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageSettingMainBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.mypage.setting.MyPageSettingActivity
import com.aos.floney.view.signup.SignUpAgreeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageSettingMainFragment :
    BaseFragment<FragmentMyPageSettingMainBinding, MyPageSettingMainViewModel>(R.layout.fragment_my_page_setting_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    val activity = requireActivity() as MyPageSettingActivity
                    activity.startMypageActivity()
                }
            }
        }
        repeatOnStarted {
            viewModel.alarmSettingPage.collect() {
                if(it) {
                    val action = MyPageSettingMainFragmentDirections.actionMyPageSettingActivityToMyPageSettingAlarmFragment()
                    findNavController().navigate(action)
                }
            }
        }
        repeatOnStarted {
            viewModel.langaugeSettingPage.collect() {
                if(it) {
                    val action = MyPageSettingMainFragmentDirections.actionMyPageSettingActivityToMyPageSettingLanguageFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}