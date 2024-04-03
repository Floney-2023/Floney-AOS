package com.aos.floney.view.book.add

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
    }
}