package com.aos.floney.view.analyze

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.library.baseAdapters.BR
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentAnalyzeOutComeBinding
import com.aos.model.analyze.AnalyzeResult
import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyzeOutComeFragment :
    BaseFragment<FragmentAnalyzeOutComeBinding, AnalyzeOutComeViewModel>(R.layout.fragment_analyze_out_come),
    UiAnalyzeCategoryOutComeModel.OnItemClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
        setUpUi()
    }

    private fun setUpUi() {
        binding.setVariable(BR.eventHolder, this)
    }

    private fun setUpViewModelObserver() {
        viewModel.postAnalyzeOutComeCategoryResult.observe(viewLifecycleOwner) {
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
    }

    override fun onItemClick(item: AnalyzeResult) {

    }

}