package com.aos.floney.view.mypage.main.service

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageServiceAskBinding
import com.aos.floney.databinding.FragmentMyPageServicePrivacyBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyPageServicePrivacyFragment : BaseFragment<FragmentMyPageServicePrivacyBinding, MyPageServicePrivacyViewModel>(R.layout.fragment_my_page_service_privacy) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wvPrivacy.loadUrl("https://m.cafe.naver.com/floney/4")
        binding.wvPrivacy.settings.javaScriptEnabled = true

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
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