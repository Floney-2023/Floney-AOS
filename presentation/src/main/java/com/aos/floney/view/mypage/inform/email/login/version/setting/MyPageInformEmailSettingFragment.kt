package com.aos.floney.view.mypage.inform.email.login.version.setting

import android.os.Bundle
import android.view.View
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageInformEmailSettingBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.mypage.inform.email.login.version.MyPageInformEmailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageInformEmailSettingFragment :
    BaseFragment<FragmentMyPageInformEmailSettingBinding, MyPageInformEmailSettingViewModel>(R.layout.fragment_my_page_inform_email_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    val activity = requireActivity() as MyPageInformEmailActivity
                    activity.startMyPageActivity()
                }
            }
        }
    }
}