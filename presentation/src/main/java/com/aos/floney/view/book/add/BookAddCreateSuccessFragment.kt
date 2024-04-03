package com.aos.floney.view.book.add

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.databinding.FragmentBookAddCreateSuccessBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
@AndroidEntryPoint
class BookAddCreateSuccessFragment : BaseFragment<FragmentBookAddCreateSuccessBinding, BookAddCreateSuccessViewModel>(R.layout.fragment_book_add_create_success) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 작성하러 가기 -> 홈 화면으로 이동
            viewModel.nextPage.collect() {
                if(it) {
                    val activity = requireActivity() as BookAddActivity
                    activity.startHomeActivity()
                }
            }
        }
        repeatOnStarted {
            // 친구초대 bottomsheet 올라오게 하기
            viewModel.inviteDailog.collect() {
                if(it) {
                    val inviteCode = viewModel.inviteCode.value ?: ""
                    val bottomSheetFragment = BookAddInviteShareBottomSheetFragment.newInstance(inviteCode)
                    bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
                }
            }
        }
    }
    private fun onSharedBtnClicked() {
        val code = viewModel.inviteCode.value ?: ""
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/html"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, code)
        startActivity(Intent.createChooser(sharingIntent, "Share using text"))
    }
}