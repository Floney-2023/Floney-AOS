package com.aos.floney.view.analyze

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import timber.log.Timber

class AnalyzeAssetBarChart(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF6200EE.toInt() // 예제 색상
        style = Paint.Style.FILL
    }

    var data = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        Timber.e("value $data")
        Timber.e("height.toFloat ${height.toFloat()}")

        val left = 0f
        val right = 20f
        val top = height.toFloat()
        val bottom = height.toFloat() - (height.toFloat() / 100 * data)
        Timber.e("top ${top}")
        Timber.e("bottom ${bottom}")

        // 바의 색상 설정
        paint.color = Color.parseColor("#31C690")

        val path = Path()
        // 특정 모서리에만 라운드 적용
        // 왼쪽 하단 시작점
        // 왼쪽 하단부터 시작
        path.moveTo(left, bottom + 5)

        path.cubicTo(left, bottom + 5, left, bottom + 5, left, bottom)

        path.cubicTo(right, bottom, right, bottom, right, top - 10)

        path.cubicTo(right, top - 10 , right, top, left, top - 6)

        path.cubicTo(left, top - 6 , left, bottom + 10, left, bottom + 10)

//        path.lineTo(right, top - 20)
//        path.cubicTo(right, top, right, top, left + 20, top)
//
//        path.moveTo(right, bottom)
//
//        path.lineTo(right, top)

//        path.cubicTo(right, top, left, top, left - 4, top)
//
//        path.lineTo(left - 20, bottom)
////
//        path.lineTo(left - 20, top)
////
//        path.cubicTo(left, top, left, bottom, left - 20, bottom)
//
        path.close()

        canvas.drawPath(path, paint)
    }

    fun setChartData(newData: Float) {
        data = newData
    }
}
