package com.aos.floney.view.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSignUpInputEmailBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SignUpInputEmailFragment : BaseFragment<FragmentSignUpInputEmailBinding, SignUpInputEmailViewModel>(R.layout.fragment_sign_up_input_email) {

    private val activityViewModel: SignUpViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()

        viewModel.setEmail(activityViewModel.getSocialUserModel()?.email ?: "")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            viewModel.nextPage.collect() {
                if (it) {
                    val email = viewModel.email.value.toString()
                    val marketing = viewModel.marketing.value ?: false
                    val action =
                        SignUpInputEmailFragmentDirections.actionSignUpInputEmailFragmentToSignUpEmailCodeFragment(
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
}