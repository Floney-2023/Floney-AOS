package com.aos.floney.view.analyze

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentAnalyzeAssetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AnalyzeAssetFragment :
    BaseFragment<FragmentAnalyzeAssetBinding, AnalyzeAssetViewModel>(R.layout.fragment_analyze_asset) {
    private val activityViewModel: AnalyzeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModelObserver()
        viewModel.postAnalyzeAsset(activityViewModel.getFormatDate())
    }

    private fun setUpViewModelObserver() {
        viewModel.postAssetResult.observe(viewLifecycleOwner) {
            it.analyzeResult.forEachIndexed { index, asset ->
                Timber.e("index $index")
                Timber.e("asset.value ${asset.value}")
                when (index) {
                    0 -> binding.barchartOne.setChartData(asset.value)
                    1 -> binding.barchartTwo.setChartData(asset.value)
                    2 -> binding.barchartThree.setChartData(asset.value)
                    3 -> binding.barchartFour.setChartData(asset.value)
                    4 -> binding.barchartFive.setChartData(asset.value)
                    5 -> binding.barchartThisMonth.setChartData(asset.value)
                }
            }
        }

        lifecycleScope.launch {
            activityViewModel.onChangedDate.collect {
                viewModel.postAnalyzeAsset(it)
            }
        }
    }
}