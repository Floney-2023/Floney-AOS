package com.aos.floney.view.settleup

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSettleUpOutcomesSelectBinding
import com.aos.floney.databinding.FragmentSettleUpPeriodSelectBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.Outcomes
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiOutcomesSelectModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SettleUpOutcomesSelectFragment : BaseFragment<FragmentSettleUpOutcomesSelectBinding, SettleUpOutcomesSelectViewModel>(R.layout.fragment_settle_up_outcomes_select) , UiOutcomesSelectModel.OnItemClickListener {
    override fun onItemClick(item: Outcomes) {
        viewModel.settingSettlementOutcomes(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@SettleUpOutcomesSelectFragment)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 다음 페이지 이동
            viewModel.back.collect {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }

        repeatOnStarted {
            // 다음 페이지 이동
            viewModel.nextPage.collect {
                if(it) {
                    val action = SettleUpOutcomesSelectFragmentDirections
                        .actionSettleUpOutcomesSelectFragmentToSettleUpCompleteFragment(
                            viewModel.memberArray.value!!,
                            viewModel.startDate.value!!,
                            viewModel.endDate.value!!,
                            viewModel.outcome.value!!,
                            viewModel.userEmail.value!!)
                    findNavController().navigate(action)
                }
            }
        }

        repeatOnStarted {
            // 처음 정산 페이지로
            viewModel.settlementPage.collect {
                if(it) {
                    val activity = requireActivity() as SettleUpActivity
                    activity.startSettleUpActivity()
                }
            }
        }

    }

}