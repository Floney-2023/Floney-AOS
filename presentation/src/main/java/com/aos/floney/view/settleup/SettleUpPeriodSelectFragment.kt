package com.aos.floney.view.settleup

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentSettleUpPeriodSelectBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SettleUpPeriodSelectFragment : BaseFragment<FragmentSettleUpPeriodSelectBinding, SettleUpPeriodSelectViewModel>(R.layout.fragment_settle_up_period_select)  {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpViewModelObserver()
    }
    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this@SettleUpPeriodSelectFragment)
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
            // 다음 페이지 이동
            viewModel.nextPage.collect {
                if(it) {
                    val action =SettleUpPeriodSelectFragmentDirections
                        .actionSettleUpPeriodSelectFragmentToSettleUpOutcomesSelectFragment(viewModel.memberArray.value!!,viewModel.startDay.value!!,viewModel.endDay.value!!)
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
        repeatOnStarted {
            viewModel.periodBottomSheetPage.collect {
                if (it) {
                    val bottomSheetFragment = SettleUpPeriodRangeSelectBottomSheetFragment { startDay, endDay, stringFormat ->
                        if (stringFormat!=null)
                            viewModel.settingSelectDay(stringFormat, startDay,endDay)
                    }
                    bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
                }
            }
        }

    }

}