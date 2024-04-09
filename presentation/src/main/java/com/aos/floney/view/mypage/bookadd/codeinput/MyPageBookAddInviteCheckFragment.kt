package com.aos.floney.view.mypage.bookadd.codeinput

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.databinding.FragmentBookAddInviteCheckBinding
import com.aos.floney.databinding.FragmentMyPageBookAddInviteCheckBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyPageBookAddInviteCheckFragment : BaseFragment<FragmentMyPageBookAddInviteCheckBinding, MyPageBookAddInviteCheckViewModel>(R.layout.fragment_my_page_book_add_invite_check) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.back.collect() {
                if(it) {
                    val activity = requireActivity() as MyPageBookCodeInputActivity
                    activity.startMyPageActivity()
                }
            }
        }
        repeatOnStarted {
            // 가계부 초대 완료 페이지 이동
            viewModel.codeInputCompletePage.collect {
                Timber.e("nextPage $it")
                if(it) {
                    val action =
                        MyPageBookAddInviteCheckFragmentDirections.actionMyPageBookAddInputBookNameFragmentToMyPageBookAddInviteSuccessFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}