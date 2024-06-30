package com.aos.floney.view.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSignUpEmailCodeBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class SignUpEmailCodeFragment : BaseFragment<FragmentSignUpEmailCodeBinding, SignUpEmailCodeViewModel>(R.layout.fragment_sign_up_email_code) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
        codeEditTextAutoFocus()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.nextPage.collect() {
                if (it) {
                    val email = viewModel.email.value ?: ""
                    val marketing = viewModel.marketing.value ?: false
                    val action =
                        SignUpEmailCodeFragmentDirections.actionSignUpEmailCodeFragmentToSignUpInputInfoFragment(
                            email,
                            marketing
                        )
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

    // editText 자동 포커싱
    private fun codeEditTextAutoFocus() {
        val editTexts = listOf(
            binding.etCodeFirst,
            binding.etCodeSecond,
            binding.etCodeThird,
            binding.etCodeFour,
            binding.etCodeFifth,
            binding.etCodeSix
        )

        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.isNullOrBlank() && start == 0 && i > 0) {
                        // 현재 EditText가 비어 있고, 사용자가 이전으로 지우기를 시도하는 경우
                        editTexts[i - 1].requestFocus()
                    } else if (s?.length == 1 && i < editTexts.lastIndex) {
                        // 현재 EditText에 값이 입력되고, 마지막 EditText가 아닌 경우 다음 칸으로 이동
                        editTexts[i + 1].requestFocus()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            editTexts[i].setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    // 백스페이스 키가 눌렸을 때의 동작
                    if (editTexts[i].text.isEmpty() && i > 0) {
                        // 현재 EditText가 비어 있을 때 이전 EditText로 포커스 이동
                        editTexts[i - 1].requestFocus()
                        true // 이벤트를 처리했음을 알림
                    } else {
                        false
                    }
                } else {
                    false
                }
            }
        }
    }

}