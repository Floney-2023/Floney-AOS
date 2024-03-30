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
import com.aos.floney.databinding.FragmentSignUpAgreeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpAgreeFragment : BaseFragment<FragmentSignUpAgreeBinding, SignUpAgreeViewModel>(R.layout.fragment_sign_up_agree) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
    }

    private fun setUpViewModelObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            // 다음 페이지 이동
            viewModel.nextPage.collect {
                if(it) {
                    val action =
                        SignUpAgreeFragmentDirections.actionSignUpAgreeFragmentToSignUpInputEmailFragment(viewModel.marketingTerms.value ?: false)
                    findNavController().navigate(action)
                }
            }
        }

    }

}