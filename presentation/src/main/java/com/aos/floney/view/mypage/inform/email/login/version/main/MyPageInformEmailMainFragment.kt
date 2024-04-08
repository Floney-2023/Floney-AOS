package com.aos.floney.view.mypage.inform.email.login.version.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageInformEmailMainBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.mypage.inform.email.login.version.MyPageInformEmailActivity
import com.aos.floney.view.mypage.inform.withdraw.MyPageInformWithdrawInputPasswordFragmentDirections
import com.aos.floney.view.mypage.setting.main.MyPageSettingMainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageInformEmailMainFragment :
    BaseFragment<FragmentMyPageInformEmailMainBinding, MyPageInformEmailMainViewModel>(R.layout.fragment_my_page_inform_email_main) {

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
        repeatOnStarted {
            viewModel.pwChangePage.collect() {
                if(it) {
                    val pwchangeAction = MyPageInformEmailMainFragmentDirections.actionMyPageInformEmailSettingFragmentToMyPageInformEmailPwChangeFragment()
                    findNavController().navigate(pwchangeAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.profileChangePage.collect() {
                if(it) {
                    val profileChangeAction = MyPageInformEmailMainFragmentDirections.actionMyPageInformEmailSettingFragmentToMyPageInformProfileChangeFragment()
                    findNavController().navigate(profileChangeAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.logOutPage.collect() {
                if(it) {
                    val activity = requireActivity() as MyPageInformEmailActivity
                    activity.startLoginActivity()
                }
            }
        }
        repeatOnStarted {
            viewModel.withDrawalPage.collect() {
                if(it) {
                    val withdrawAction =
                        MyPageInformEmailMainFragmentDirections.actionMyPageInformEmailSettingFragmentToMyPageInformWithdrawReasonCheckFragment()
                    findNavController().navigate(withdrawAction)
                }
            }
        }
    }
}