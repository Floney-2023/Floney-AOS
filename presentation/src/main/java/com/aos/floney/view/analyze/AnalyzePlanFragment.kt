package com.aos.floney.view.analyze

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentAnalyzePlanBinding
import com.aos.floney.ext.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnalyzePlanFragment : BaseFragment<FragmentAnalyzePlanBinding, AnalyzePlanViewModel>(R.layout.fragment_analyze_plan) {

    private val activityViewModel: AnalyzeViewModel by activityViewModels()
    override val applyTransition: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
        viewModel.postAnalyzePlan(activityViewModel.getFormatDate())
    }

    // 최대는 194
    // 1% = 1.94
    private fun setUpCircleBarChart(value: Int) {
        binding.circleView.setPercent(value)
        //binding.circleView.setPercentWithAnimation(10)
    }

    private fun setUpViewModelObserver() {
        viewModel.postAnalyzePlan.observe(viewLifecycleOwner) {
            setUpCircleBarChart(it.percent.toInt())
        }

        repeatOnStarted {
            viewModel.onClickSetBudget.collect {
                val activityViewModels: AnalyzeViewModel by activityViewModels()
                activityViewModels.onClickSetBudget(true)
            }
        }

        activityViewModel.onClickSetBudget.observe(viewLifecycleOwner) {
            if(!it) {
                viewModel.postAnalyzePlan(activityViewModel.getFormatDate())
            }
        }

        lifecycleScope.launch {
            activityViewModel.onChangedDate.collect {
                viewModel.postAnalyzePlan(it)
            }
        }
    }

}