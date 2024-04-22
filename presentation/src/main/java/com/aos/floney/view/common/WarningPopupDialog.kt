package com.aos.floney.view.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.aos.floney.databinding.WarningPopupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WarningPopupDialog(
    val title : String,
    val info : String,
    val leftButton : String,
    val rightButton : String,
    private val onSelect: (Boolean) -> Unit) :
    DialogFragment(){

    private var _binding: WarningPopupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = WarningPopupBinding.inflate(inflater, container, false)
        val view = binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setUpUi()
        setUpListener()
        return view
    }
    private fun setUpUi()
    {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding.tvPopupTitle.text = title
        binding.tvPopupInfo.text = info
        binding.btnLeft.text = leftButton
        binding.btnRight.text = rightButton
    }
    private fun setUpListener()
    {
        binding.btnLeft.setOnClickListener {
            onSelect(true)
            dismiss()
        }

        binding.btnRight.setOnClickListener {
            onSelect(false)
            dismiss()
        }
    }
}