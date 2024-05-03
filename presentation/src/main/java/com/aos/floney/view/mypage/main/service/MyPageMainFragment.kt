package com.aos.floney.view.mypage.main.service

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseActivity
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.ActivityMyPageBinding
import com.aos.floney.databinding.FragmentMyPageMainBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.analyze.AnalyzeActivity
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.home.HomeMonthTypeFragment
import com.aos.floney.view.mypage.MyPageActivity
import com.aos.floney.view.mypage.alarm.MyPageAlarmActivity
import com.aos.floney.view.mypage.bookadd.MypageBookAddSelectBottomSheetFragment
import com.aos.floney.view.mypage.bookadd.codeinput.MyPageBookCodeInputActivity
import com.aos.floney.view.mypage.bookadd.create.MyPageBookCreateActivity
import com.aos.floney.view.mypage.inform.MyPageInformActivity
import com.aos.floney.view.mypage.main.service.MyPageServicePrivacyFragment
import com.aos.floney.view.mypage.main.service.MyPageServiceTermsFragment
import com.aos.floney.view.mypage.main.service.MyPageServiceTermsViewModel
import com.aos.floney.view.mypage.setting.MyPageSettingActivity
import com.aos.floney.view.settleup.SettleUpActivity
import com.aos.floney.view.signup.SignUpActivity
import com.aos.model.user.MyBooks
import com.aos.model.user.UiMypageSearchModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageMainFragment : BaseFragment<FragmentMyPageMainBinding, MyPageMainViewModel>(R.layout.fragment_my_page_main), UiMypageSearchModel.OnItemClickListener {

    override fun onItemClick(item: MyBooks) {
        viewModel.settingBookKey(item.bookKey)
    }
    override fun onResume() {
        super.onResume()
        viewModel.searchMypageItems()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@MyPageMainFragment)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.alarmPage.collect {
                if(it) {
                    val activity = requireActivity() as MyPageActivity
                    activity.startAlarmActivity()
                }
            }
        }
        repeatOnStarted {
            viewModel.informPage.collect {
                if(it) {
                    val activity = requireActivity() as MyPageActivity
                    activity.startInformActivity()
                }
            }
        }
        repeatOnStarted {
            viewModel.settingPage.collect {
                if(it) {

                    val activity = requireActivity() as MyPageActivity
                    activity.startSettingActivity()
                }
            }
        }
        repeatOnStarted {
            viewModel.bookAddBottomSheet.collect { shouldShowBottomSheet ->
                if (shouldShowBottomSheet) {

                    val activity = requireActivity() as MyPageActivity
                    activity.startBottomSheet()
                }
            }
        }
        repeatOnStarted {
            viewModel.mailPage.collect {
                if (it){
                    val askAction = MyPageMainFragmentDirections.actionMyPageMainFragmentToMyPageServiceAskFragment()
                    findNavController().navigate(askAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.noticePage.collect {
                if (it){
                    val noticeAction = MyPageMainFragmentDirections.actionMyPageMainFragmentToMyPageServiceNoticeFragment()
                    findNavController().navigate(noticeAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.privatePage.collect {
                if (it){
                    val privateAction = MyPageMainFragmentDirections.actionMyPageMainFragmentToMyPageServicePrivacyFragment()
                    findNavController().navigate(privateAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.usageRightPage.collect {
                if (it){
                    val rightAction = MyPageMainFragmentDirections.actionMyPageMainFragmentToMyPageServiceTermsFragment()
                    findNavController().navigate(rightAction)
                }
            }
        }
    }
}