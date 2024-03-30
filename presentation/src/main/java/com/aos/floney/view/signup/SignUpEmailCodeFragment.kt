package com.aos.floney.view.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSignUpEmailCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpEmailCodeFragment : BaseFragment<FragmentSignUpEmailCodeBinding, SignUpEmailCodeViewModel>(R.layout.fragment_sign_up_email_code) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        codeEditTextAutoFocus()
    }

    // editText 자동 포커싱
    private fun codeEditTextAutoFocus() {
        binding.etCodeFirst.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count > 0) {
                    binding.etCodeSecond.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etCodeSecond.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count > 0) {
                    binding.etCodeThird.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etCodeThird.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count > 0) {
                    binding.etCodeFour.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etCodeFour.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count > 0) {
                    binding.etCodeFifth.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etCodeFifth.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(count > 0) {
                    binding.etCodeSix.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

}