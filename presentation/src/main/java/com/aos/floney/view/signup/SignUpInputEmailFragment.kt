package com.aos.floney.view.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSignUpInputEmailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpInputEmailFragment : BaseFragment<FragmentSignUpInputEmailBinding, SignUpInputEmailViewModel>(R.layout.fragment_sign_up_input_email) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.nextPage.observe(viewLifecycleOwner) {
                if(it) {
                    val email = viewModel.email.value.toString()
                    val marketing = viewModel.marketing.value ?: false
                    val action =
                        SignUpInputEmailFragmentDirections.actionSignUpInputEmailFragmentToSignUpEmailCodeFragment(email, marketing)
                    findNavController().navigate(action)
                }
            }
        }
    }

}