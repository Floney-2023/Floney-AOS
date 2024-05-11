package com.aos.floney.view.settleup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
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

    private val activityViewModel: SettleUpViewModel by activityViewModels()

    override fun onItemClick(item: Settlement) {
        viewModel.seeDetailSettlement(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
        setUpNavigate()
    }
    private fun setUpUi() {
        activityViewModel.bottomSee(false)
        binding.setVariable(BR.eventHolder, this@SettleUpSeeFragment)
    }

    private fun setUpViewModelObserver() {
        repeatOnStarted {
            // 처음 정산 페이지
            viewModel.startPage.collect {
                if(it) {
                    val activity = requireActivity() as SettleUpActivity
                    activity.startSettleUpActivity()
                }
            }
        }
        repeatOnStarted {
            // 정산 페이지 권한 설정 불가..
            viewModel.homePage.collect {
                if(it) {
                    val activity = requireActivity() as SettleUpActivity
                    activity.startHomeActivity()
                }
            }
        }
        repeatOnStarted {
            // 정산 상세 페이지
            viewModel.settlementDetailPage.collect {
                if(it>0) {
                    val action =
                        SettleUpSeeFragmentDirections.actionSettleUpSeeFragmentToSettleUpDetailSeeFragment(it)
                    findNavController().navigate(action)
                }
            }
        }

    }
    private fun setUpNavigate(){
        if (viewModel.id.value?.toInt() !=-1) { // 원링크 이동 시, 상세 페이지로 이동
            val action =
                SettleUpSeeFragmentDirections.actionSettleUpSeeFragmentToSettleUpDetailSeeFragment(
                    viewModel.id.value!!.toLong()
                )
            viewModel.settingReset() // 값 리셋. 원링크 이전 화면 이동 시, 자동 다음 화면 방지
            findNavController().navigate(action)
        }
    }
}