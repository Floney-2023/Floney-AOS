package com.aos.floney.view.mypage.inform.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageInformMainBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.common.BaseAlertDialog
import com.aos.floney.view.mypage.inform.MyPageInformActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageInformMainFragment :
    BaseFragment<FragmentMyPageInformMainBinding, MyPageInformMainViewModel>(R.layout.fragment_my_page_inform_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    val activity = requireActivity() as MyPageInformActivity
                    activity.startMyPageActivity()
                }
            }
        }
        repeatOnStarted {
            viewModel.pwChangePage.collect() {
                if(it) {
                    val pwchangeAction = MyPageInformMainFragmentDirections.actionMyPageInformEmailSettingFragmentToMyPageInformEmailPwChangeFragment()
                    findNavController().navigate(pwchangeAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.profileChangePage.collect() {
                if(it) {
                    val profileChangeAction = MyPageInformMainFragmentDirections.actionMyPageInformEmailSettingFragmentToMyPageInformProfileChangeFragment()
                    findNavController().navigate(profileChangeAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.logOutAlert.collect() {
                if(it) {
                    val logOutDialog = BaseAlertDialog(
                        getString(R.string.mypage_main_inform_logout),
                        getString(R.string.mypage_main_inform_logout_dialog_info),
                        false
                    ) {  checked ->
                        if (checked)
                            viewModel.onLogOut()
                    }
                    logOutDialog.show(parentFragmentManager, "initDialog")
                }
            }
        }
        repeatOnStarted {
            viewModel.logOutPage.collect(){
                if(it){
                    val activity = requireActivity() as MyPageInformActivity
                    activity.startLoginActivity()
                }
            }
        }
        repeatOnStarted {
            viewModel.withDrawalPage.collect() {
                if(it) {
                    val withdrawAction =
                        MyPageInformMainFragmentDirections.actionMyPageInformEmailSettingFragmentToMyPageInformWithdrawReasonCheckFragment()
                    findNavController().navigate(withdrawAction)
                }
            }
        }
    }
}