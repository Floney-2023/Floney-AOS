package com.aos.floney.view.settleup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSettleUpMemberSelectBinding
import com.aos.floney.databinding.FragmentSettleUpSeeBinding
import com.aos.floney.databinding.FragmentSettleUpStartBinding
import com.aos.floney.databinding.FragmentSignUpAgreeBinding
import com.aos.floney.ext.repeatOnStarted
import com.aos.floney.view.home.HomeActivity
import com.aos.floney.view.signup.SignUpActivity
import com.aos.model.home.MonthMoney
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.Settlement
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiSettlementSeeModel
import com.aos.model.user.MyBooks
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SettleUpSeeFragment : BaseFragment<FragmentSettleUpSeeBinding, SettleUpSeeViewModel>(R.layout.fragment_settle_up_see) , UiSettlementSeeModel.OnItemClickListener {

    override fun onItemClick(item: Settlement) {
        viewModel.seeDetailSettlement(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@SettleUpSeeFragment)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 다음 페이지 이동
            viewModel.startPage.collect {
                if(it) {
                    val activity = requireActivity() as SettleUpActivity
                    activity.startSettleUpActivity()
                }
            }
        }

        repeatOnStarted {
            // 처음 정산 페이지로
            viewModel.settlementDetailPage.collect {
                if(it>0) {
                    val action =
                        SettleUpSeeFragmentDirections.actionSettleUpSeeFragmentToSettleUpDetailSeeFragment(it)
                    findNavController().navigate(action)
                }
            }
        }
    }
}