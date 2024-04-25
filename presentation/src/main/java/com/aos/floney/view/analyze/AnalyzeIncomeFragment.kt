package com.aos.floney.view.analyze

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentAnalyzeIncomeBinding
import com.aos.model.analyze.AnalyzeResult
import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnalyzeIncomeFragment : BaseFragment<FragmentAnalyzeIncomeBinding, AnalyzeIncomeViewModel>(R.layout.fragment_analyze_income) {

    private val activityViewModel: AnalyzeViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
        viewModel.postAnalyzeCategory(activityViewModel.getFormatDate())
    }

    private fun setUpViewModelObserver() {
        viewModel.postAnalyzeInComeCategoryResult.observe(viewLifecycleOwner) {
            binding.chartStackHorizontal.isVisible = false
            val colorArr = arrayListOf<Int>()
            it.analyzeResult.forEachIndexed { index, analyzeResult ->
                if (index > 2) {
                    colorArr.add(analyzeResult.color)
                }
            }

            binding.chartStackHorizontal.setData(
                it.analyzeResult.map { it.percent.toFloat() }, colorArr
            )
            binding.chartStackHorizontal.isVisible = true
            binding.chartStackHorizontal.clearColorIdx()
        }

        lifecycleScope.launch {
            activityViewModel.onChangedDate.collect {
                viewModel.postAnalyzeCategory(it)
            }
        }
    }

}