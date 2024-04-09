package com.aos.floney.view.mypage.bookadd.create

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.ActivityBookAddBinding
import com.aos.floney.databinding.FragmentBookAddSettingProfileBinding
import com.aos.floney.databinding.FragmentMyPageBookAddSettingProfileBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyPageBookAddSettingProfileFragment : BaseFragment<FragmentMyPageBookAddSettingProfileBinding, MyPageBookAddSettingProfileViewModel>(R.layout.fragment_my_page_book_add_setting_profile) {

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
                        MyPageBookAddSettingProfileFragmentDirections.actionMyPageBookAddSettingProfileFragmentToMyPageBookAddCreateSuccessFragment(code)
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