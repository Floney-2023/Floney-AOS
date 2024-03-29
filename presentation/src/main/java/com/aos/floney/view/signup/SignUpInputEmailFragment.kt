package com.aos.floney.view.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSignUpInputEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpInputEmailFragment : BaseFragment<FragmentSignUpInputEmailBinding, SignUpInputEmailViewModel>(R.layout.fragment_sign_up_input_email) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}