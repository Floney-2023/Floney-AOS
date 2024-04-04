package com.aos.floney.view.book.add

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.databinding.FragmentBookAddSettingProfileBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BookAddSettingProfileFragment : BaseFragment<FragmentBookAddSettingProfileBinding, BookAddSettingProfileViewModel>(R.layout.fragment_book_add_setting_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 다음 페이지 이동
            viewModel.nextPage.collect {
                Timber.e("nextPage $it")
                if(it) {
                    val code = viewModel.inviteCode.value ?: ""
                    val action =
                        BookAddSettingProfileFragmentDirections.actionBookAddSettingProfileFragmentToBookAddCreateSuccessFragment(code)
                    findNavController().navigate(action)
                }
            }
        }
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