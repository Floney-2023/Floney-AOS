package com.aos.floney.view.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSignUpEmailCodeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignUpEmailCodeFragment : BaseFragment<FragmentSignUpEmailCodeBinding, SignUpEmailCodeViewModel>(R.layout.fragment_sign_up_email_code) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        codeEditTextAutoFocus()
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
        }
    }

}