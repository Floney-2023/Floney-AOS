package com.aos.floney.view.book.setting

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentBookSettingEditBinding
import com.aos.floney.databinding.FragmentMyPageInformEmailMainBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.mypage.inform.email.login.version.MyPageInformEmailActivity
import com.aos.floney.view.mypage.inform.withdraw.MyPageInformWithdrawInputPasswordFragmentDirections
import com.aos.floney.view.mypage.setting.main.MyPageSettingMainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookSettingEditFragment :
    BaseFragment<FragmentBookSettingEditBinding, BookSettingEditViewModel> (R.layout.fragment_book_setting_edit){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }
        repeatOnStarted {
            viewModel.profileChangePage.collect() {
                if(it) {
                    val profileChangeAction = BookSettingEditFragmentDirections.actionBookSettingEditFragmentToBookSettingProfileChangeFragment(
                        viewModel.profileImg.value!!,
                        viewModel.profileCheck.value!!,
                        viewModel.roleCheck.value!!
                    )
                    findNavController().navigate(profileChangeAction)
                }
            }
        }
        repeatOnStarted {
            viewModel.deletePage.collect() {
                if(it) {
                    // 가계부 1개 있는데 삭제한 경우 -> 가계부 추가 화면으로 이동
                    if (viewModel.bookCount.value == 1)
                    {
                        val activity = requireActivity() as BookSettingActivity
                        activity.startBookAddActivity()
                    }
                    else { // 가계부 2개 이상인데 삭제한 경우 -> 홈 화면으로 이동
                        val activity = requireActivity() as BookSettingActivity
                        activity.startHomeActivity()
                    }
                }
            }
        }
    }
}