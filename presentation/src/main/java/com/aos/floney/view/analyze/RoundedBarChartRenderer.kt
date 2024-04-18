package com.aos.floney.view.analyze

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
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
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFF6200EE.toInt() // 예제 색상
            style = Paint.Style.FILL
        }


        for (i in 0 until dataSet.entryCount) {
            val entry = dataSet.getEntryForIndex(i)
            val vals = entry.yVals  // 스택된 값들을 가져옵니다.
            val total = entry.yVals.sum() // 데이터 총합 계산

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

                for (stackIndex in vals.indices) {
                    val value = vals[stackIndex]
//                    val left = entry.x - barWidth / 2
//                    val right = entry.x + barWidth / 2
//                    val top = (accumulatedValue + value) * phaseY
//                    val bottom = accumulatedValue * phaseY
                    val width = phaseX * (value.toFloat() / total)
                    val left = accumulatedValue
                    val right = left + width
                    val bottom = 0f
                    val top = phaseY.toFloat() // 뷰의 높이

                    accumulatedValue += width

                    // 바의 색상 설정
                    paint.color = when (index) {
                        0 -> Color.parseColor("#FFDE31")
                        1 -> Color.parseColor("#FF965B")
                        2 -> Color.parseColor("#E56E73")
                        else -> Color.TRANSPARENT
                    }


                    Timber.e("value $value")
                    Timber.e("left $left")
                    Timber.e("right $right")
                    Timber.e("bottom $bottom")
                    Timber.e("top $top")
                    Timber.e("stackIndex $stackIndex")
                    if(stackIndex == 0) {
                        val path = Path()
                        // 특정 모서리에만 라운드 적용
                        // 왼쪽 하단 시작점
                        // 왼쪽 하단부터 시작
                        path.moveTo(right, bottom)

                        path.lineTo(left + 20, bottom)

                        path.moveTo(right, bottom)

                        path.lineTo(right, top)

                        path.lineTo(left + 20, top)

                        path.cubicTo(left, top, left, bottom, left + 20, bottom)

                        path.close()

                        c.drawPath(path, paint)
                    } else if(stackIndex == vals.size - 1){
                        val path = Path()
                        path.moveTo(left, bottom)

                        path.lineTo(right - 20, bottom)

                        path.moveTo(left, bottom)

                        path.lineTo(left, top)

                        path.lineTo(right - 20, top)

                        path.cubicTo(right, top, right, bottom, right - 20, bottom)

                        path.close()

                        c.drawPath(path, paint)
                    } else {
                        c.drawRect(left, top, right, bottom, paint)
                    }
                }
            }
        }
    }
}