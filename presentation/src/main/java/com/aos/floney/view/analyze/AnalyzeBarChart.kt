package com.aos.floney.view.analyze

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import timber.log.Timber

class AnalyzeBarChart (context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF6200EE.toInt() // 예제 색상
        style = Paint.Style.FILL
    }

    private var data = listOf(40, 40, 20) // 차트 데이터
    private val total = data.sum() // 데이터 총합 계산

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var individualWidth = 0f

        for ((index, value) in data.withIndex()) {
            val width = width * (value.toFloat() / total)
            val left = individualWidth
            val right = left + width
            val bottom = 0f
            val top = height.toFloat() // 뷰의 높이

            individualWidth += width

            // 바의 색상 설정
            paint.color = when (index) {
                0 -> Color.parseColor("#FFDE31")
                1 -> Color.parseColor("#FF965B")
                2 -> Color.parseColor("#E56E73")
                else -> Color.TRANSPARENT
            }

            Timber.e("data.size - 1 ${data.size - 1}")
            if(index == 0) {
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

                canvas.drawPath(path, paint)
            } else if(index == data.size - 1){
                val path = Path()
                path.moveTo(left, bottom)

                path.lineTo(right - 20, bottom)

                path.moveTo(left, bottom)

                path.lineTo(left, top)

                path.lineTo(right - 20, top)

                path.cubicTo(right, top, right, bottom, right - 20, bottom)

                path.close()

                canvas.drawPath(path, paint)
            } else {
                canvas.drawRect(left, top, right, bottom, paint)
            }
        }
    }

    fun setData(newData: List<Int>) {
        data = newData
        invalidate()
    }
}