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

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window?.setGravity(Gravity.BOTTOM)
        binding.tvError.text = text

        setContentView(binding.root)
    }

}