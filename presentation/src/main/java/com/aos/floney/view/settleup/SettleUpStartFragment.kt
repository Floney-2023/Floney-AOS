package com.aos.floney.view.settleup

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
import com.aos.floney.databinding.FragmentSettleUpStartBinding
import com.aos.floney.databinding.FragmentSignUpAgreeBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SettleUpStartFragment : BaseFragment<FragmentSettleUpStartBinding, SettleUpStartViewModel>(R.layout.fragment_settle_up_start) {
    private val activityViewModel: SettleUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }

    private fun setUpUi()
    {
        activityViewModel.bottomSee(true)
    }
    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 정산 시작하기 이동
            viewModel.settleUpStartPage.collect {
                if(it) {
                    val action =
                        SettleUpStartFragmentDirections.actionSettleUpStartFragmentToSettleUpMemberSelectFragment()
                    findNavController().navigate(action)
                }
            }
        }

        repeatOnStarted {
            // 정산 내역 보기 이동
            viewModel.settleUpSeePage.collect {
                if(it) {
                    val action =
                        SettleUpStartFragmentDirections.actionSettleUpStartFragmentToSettleUpSeeFragment(-1,"")
                    findNavController().navigate(action)
                }
            }
        }

        repeatOnStarted {
            activityViewModel.sharePage.collect {
                if(it) {
                    val action =
                        SettleUpStartFragmentDirections.actionSettleUpStartFragmentToSettleUpSeeFragment(activityViewModel.id.value!!,activityViewModel.bookKey.value!!)
                    findNavController().navigate(action)
                }
            }
        }
    }

}