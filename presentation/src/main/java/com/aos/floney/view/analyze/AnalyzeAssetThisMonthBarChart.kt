package com.aos.floney.view.analyze

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import timber.log.Timber

class AnalyzeAssetThisMonthBarChart(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
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
        val right = width.toFloat()
        val top = height.toFloat()
        val bottom = height.toFloat() - (height.toFloat() / 100 * data)
        Timber.e("top ${top}")
        Timber.e("bottom ${bottom}")

        // 바의 색상 설정
        paint.color = Color.parseColor("#31C690")

        val cornerRadius = 8f // 모서리 둥글기 정도
        val rectF = RectF(left, top, right, bottom)
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
    }

    fun setChartData(newData: Float) {
        data = newData
        this.invalidate()
    }
}
