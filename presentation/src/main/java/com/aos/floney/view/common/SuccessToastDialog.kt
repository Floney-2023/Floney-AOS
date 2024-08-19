package com.aos.floney.view.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.aos.floney.databinding.DialogToastErrorBinding
import com.aos.floney.databinding.DialogToastSuccessBinding

class SuccessToastDialog(context: Context, private val text: String): Dialog(context) {
    private lateinit var binding: DialogToastSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogToastSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 다이얼로그 크기 및 위치 조정
        window?.apply {
            val params = attributes
            params.width = WindowManager.LayoutParams.MATCH_PARENT // 화면 전체 너비로 설정
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            attributes = params

            // 여백 효과를 위해 크기를 줄여줌
            setLayout((context.resources.displayMetrics.widthPixels * 0.9).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            setGravity(Gravity.BOTTOM or Gravity.CENTER) // 중앙 아래쪽에 위치시킴
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.tvError.text = text

    }

}