package com.aos.floney.view.mypage.service

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentMyPageServiceAskBinding
import com.aos.floney.databinding.FragmentMypageServiceAskBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyPageServiceAskFragment : BaseFragment<FragmentMyPageServiceAskBinding, MyPageServiceAskViewModel>(R.layout.fragment_my_page_service_ask) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 메일로 문의하기
            viewModel.nextPage.collect {
                Timber.e("nextPage $it")
                if(it) {
                    val email = Intent(Intent.ACTION_SEND)
                    email.setType("plain/text")
                    val address = arrayOf("floney.team@gmail.com")
                    email.putExtra(Intent.EXTRA_EMAIL, address)
                    email.putExtra(Intent.EXTRA_SUBJECT, "Floney 문의 제보")
                    email.putExtra(Intent.EXTRA_TEXT, "작성자 : \n 문의 기능 : \n문의 내용 : \n")
                    startActivity(email)
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