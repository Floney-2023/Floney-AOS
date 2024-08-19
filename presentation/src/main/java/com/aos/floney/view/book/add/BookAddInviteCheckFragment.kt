package com.aos.floney.view.book.add

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.databinding.FragmentBookAddInviteCheckBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookAddInviteCheckFragment : BaseFragment<FragmentBookAddInviteCheckBinding, BookAddInviteCheckViewModel>(R.layout.fragment_book_add_invite_check) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 입력 완료하기 페이지 이동
            viewModel.codeInputCompletePage.collect {
                Timber.e("nextPage $it")
                if(it) {
                    val action =
                        BookAddInviteCheckFragmentDirections.actionBookAddInviteCheckFragmentToBookAddInviteSuccessFragment()
                    findNavController().navigate(action)
                }
            }
        }
        repeatOnStarted {
            // 새 가계부 만들기 페이지 이동
            viewModel.newBookCreatePage.collect {
                Timber.e("nextPage $it")
                if(it) {
                    val action =
                        BookAddInviteCheckFragmentDirections.actionBookAddInviteCheckFragmentToBookAddInputBookNameFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}