package com.aos.floney.view.analyze

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.aos.floney.R
import com.aos.floney.base.BaseFragment
import com.aos.floney.databinding.FragmentAnalyzeOutComeBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyzeOutComeFragment : BaseFragment<FragmentAnalyzeOutComeBinding, AnalyzeOutComeViewModel>(R.layout.fragment_analyze_out_come) {

    val colocArrayList = mutableListOf<Int>(Color.parseColor("#FFDE31"), Color.parseColor("#FF965B"), Color.parseColor("#E56E73"))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpChart()

        val barDataSet = BarDataSet(getDataValues(), "Bar Set")
        barDataSet.colors = colocArrayList
        barDataSet.setDrawValues(false)

        val barData = BarData(barDataSet)
        barData.barWidth = 20f

        binding.chartStackHorizontal.data = barData
//        binding.chartStackHorizontal.renderer = RoundedBarChartRenderer(binding.chartStackHorizontal, binding.chartStackHorizontal.animator, binding.chartStackHorizontal.viewPortHandler)
        binding.chartStackHorizontal.invalidate()
    }

    private fun setUpChart() {
        // X 축 설정
        val xAxis = binding.chartStackHorizontal.xAxis

        xAxis.run {
            setDrawLabels(false)
            setDrawGridLines(false)
            setDrawAxisLine(false)
            position = XAxis.XAxisPosition.BOTH_SIDED
        }

        // 왼쪽 Y 축
        val leftYAxis = binding.chartStackHorizontal.axisLeft

        leftYAxis.run {
            setDrawLabels(false)
            setDrawGridLines(false)
            setDrawAxisLine(false)
            setDrawZeroLine(false)
            axisMaximum = 100f
            axisMinimum = 0f
        }


        // 오른쪽 Y 축
        val rightYAxis = binding.chartStackHorizontal.axisRight

        rightYAxis.run {
            setDrawLabels(false)
            setDrawGridLines(false)
            setDrawAxisLine(false)
            setDrawZeroLine(false)
        }

        binding.chartStackHorizontal.run {
            setFitBars(true) //그래프의 바가 그래프영역에 맞게 표시 되도록 설정, true 시 자동조절
            description.isEnabled = false //그래프의 설명을 비활성화
            legend.isEnabled = false //그래프 하단에 표시되는 범례를 비활성화
//        axisRight.isEnabled = false
            setPinchZoom(false) //확대 및 축소 비활성화
            setDrawValueAboveBar(true) // 바 아래 표시
            setDrawGridBackground(false) // 배경 그리드 숨김
            setDrawBorders(false) // 바깥 테두리 숨김
            animateY(0) //애니메이션 없애기
            setTouchEnabled(false) //터치 막기
        }
    }

    private fun getDataValues(): ArrayList<BarEntry> {
        val dataVals = arrayListOf<BarEntry>()
        dataVals.add(BarEntry(0f, floatArrayOf(50f, 40f, 10f)))
        return dataVals
    }

}