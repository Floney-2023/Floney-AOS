package com.aos.floney.view.mypage.inform.simple.login.version.main

import android.os.Bundle
import android.view.View
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageInformSimpleMainBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageInformSimpleMainFragment : BaseFragment<FragmentMyPageInformSimpleMainBinding, MyPageInformSimpleMainViewModel>(R.layout.fragment_my_page_inform_simple_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {

                }
            }
        }
    }
}