package com.aos.floney.view.settleup

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSettleUpCompleteBinding
import com.aos.floney.databinding.FragmentSettleUpDetailSeeBinding
import com.aos.floney.databinding.FragmentSettleUpOutcomesSelectBinding
import com.aos.floney.databinding.FragmentSettleUpPeriodSelectBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.Details
import com.aos.model.settlement.Outcomes
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiOutcomesSelectModel
import com.aos.model.settlement.UiSettlementAddModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SettleUpDetailSeeFragment : BaseFragment<FragmentSettleUpDetailSeeBinding, SettleUpDetailSeeViewModel>(R.layout.fragment_settle_up_detail_see) , UiSettlementAddModel.OnItemClickListener {
    override fun onItemClick(item: Details) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@SettleUpDetailSeeFragment)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 이전 페이지 이동
            viewModel.back.collect {
                if(it) {
                    findNavController().popBackStack()
                }
            }
        }

        repeatOnStarted {
            // 처음 정산 페이지로 (공유 페이지 - 임시)
            viewModel.sharedPage.collect {
                if(it) {
                    val activity = requireActivity() as SettleUpActivity
                    activity.startSettleUpActivity()
                }
            }
        }

    }

}