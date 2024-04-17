package com.aos.floney.view.analyze

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler
import timber.log.Timber


class RoundedBarChartRenderer(
    chart: BarChart,
    animator: ChartAnimator,
    viewPortHandler: ViewPortHandler
) : BarChartRenderer(chart, animator, viewPortHandler) {

    private val barRect = RectF()

    override fun drawDataSet(c: Canvas, dataSet: IBarDataSet, index: Int) {
        val phaseX = mAnimator.phaseX
        val phaseY = mAnimator.phaseY
        val barWidth = mChart.barData.barWidth

        for (i in 0 until dataSet.entryCount) {
            val entry = dataSet.getEntryForIndex(i)
            val vals = entry.yVals  // 스택된 값들을 가져옵니다.

            if (vals == null) {
                // 스택이 없는 경우
                val left = entry.x - barWidth / 2
                val right = entry.x + barWidth / 2
                val top = entry.y * phaseY
                val bottom = 0f

                barRect.set(left, bottom, right, top)
                val radius = 20f
                mRenderPaint.style = Paint.Style.FILL // 또는 FILL_AND_STROKE
                mRenderPaint.color = dataSet.getColor(i)
                c.drawRoundRect(barRect, radius, radius, mRenderPaint)
            } else {
                // 스택이 있는 경우
                var accumulatedValue = 0f

                Timber.e("vals $vals")
                for (stackIndex in vals.indices) {
                    val value = vals[stackIndex]
//                    val left = entry.x - barWidth / 2
//                    val right = entry.x + barWidth / 2
//                    val top = (accumulatedValue + value) * phaseY
//                    val bottom = accumulatedValue * phaseY
                    val left = accumulatedValue * phaseX
                    val right = (accumulatedValue + value) * phaseX
                    val top = 100f
                    val bottom = 0f

                    Timber.e("value $value")
                    Timber.e("left $left")
                    Timber.e("right $right")
                    Timber.e("top $top")
                    Timber.e("bottom $bottom")

                    accumulatedValue += value

                    barRect.set(left, top, right, bottom )
                    val radius = 20f

                    // 여기서 색상을 적용합니다.
                    mRenderPaint.style = Paint.Style.FILL_AND_STROKE // 또는 FILL_AND_STROKE
                    mRenderPaint.color = dataSet.colors.getOrNull(stackIndex) ?: dataSet.getColor(i)
                    c.drawRoundRect(left, top, right, bottom, radius, radius, mRenderPaint)
                }
            }
        }
    }
}