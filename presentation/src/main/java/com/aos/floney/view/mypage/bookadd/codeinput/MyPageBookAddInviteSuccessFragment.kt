package com.aos.floney.view.mypage.bookadd.codeinput

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.databinding.FragmentBookAddInviteSuccessBinding
import com.aos.floney.databinding.FragmentMyPageBookAddInviteSuccessBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.signup.SignUpActivity
import com.aos.floney.view.signup.SignUpAgreeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyPageBookAddInviteSuccessFragment : BaseFragment<FragmentMyPageBookAddInviteSuccessBinding, MyPageBookAddInviteSuccessViewModel>(R.layout.fragment_my_page_book_add_invite_success) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 작성하러 가기 -> 홈 화면 가기
            viewModel.nextPage.collect {
                Timber.e("nextPage $it")
                if(it) {
                    val activity = requireActivity() as MyPageBookCodeInputActivity
                    activity.startHomeActivity()
                }
            }
        }
        repeatOnStarted {
            // 작성하러 가기 -> 홈 화면 가기
            viewModel.getInform.collect {
                Timber.e("nextPage $it")
                if(it) {
                    viewModel.saveInviteAlarm()
                }
            }
        }
    }
}