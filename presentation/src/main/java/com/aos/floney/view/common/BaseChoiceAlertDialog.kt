package com.aos.floney.view.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.aos.floney.R
import com.aos.floney.databinding.BaseAlertDialogBinding
import com.aos.floney.databinding.BaseChoiceAlertDialogBinding
import com.aos.floney.databinding.WarningPopupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseChoiceAlertDialog(
    val title : String,
    val btnTextStr1 : String,
    val btnTextStr2 : String,
    private val onSelect: (Boolean) -> Unit) :
    DialogFragment(){

    private var _binding: BaseChoiceAlertDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 다이얼로그가 취소되지 않도록 설정
//        isCancelable = false
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BaseChoiceAlertDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setUpUi()
        setUpListener()
        return view
    }
    private fun setUpUi() {
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
            attributes = attributes?.apply {
                dimAmount = 0.2f // 20% 딤 효과
            }
        }

        binding.apply {
            tvPopupTitle.text = title
            btnText1.text = btnTextStr1
            btnText2.text = btnTextStr2
        }
    }

    private fun setUpListener()
    {
        binding.btnText1.setOnClickListener {
            onSelect(true)
            dismiss()
        }

        binding.btnText2.setOnClickListener {
            onSelect(false)
            dismiss()
        }
    }
}