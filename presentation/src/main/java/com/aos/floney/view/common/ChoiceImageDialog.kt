package com.aos.floney.view.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.aos.floney.databinding.DialogChoiceImageBinding

class ChoiceImageDialog(context: Context, private val onClickTakePicture: () -> Unit, private val onClickGallery: () -> Unit, private val onClickRandom: () -> Unit ): Dialog(context) {
    private lateinit var binding: DialogChoiceImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogChoiceImageBinding.inflate(layoutInflater)
        setUpUi()
        onClickBtnListener()

        setContentView(binding.root)
    }

    private fun setUpUi() {
        this.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
    }

    private fun onClickBtnListener() {
        binding.btnTakeCapture.setOnClickListener {
            onClickTakePicture()
            dismiss()
        }
        binding.btnChoiceGallery.setOnClickListener {
            onClickGallery()
            dismiss()
        }
        binding.btnRandomImage.setOnClickListener {
            onClickRandom()
            dismiss()
        }
    }
}