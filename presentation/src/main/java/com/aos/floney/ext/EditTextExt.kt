package com.aos.floney.ext

import android.text.Editable
import android.text.InputType
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.aos.data.util.CurrencyUtil
import com.aos.data.util.checkDecimalPoint
import timber.log.Timber

@BindingAdapter("bind:setSelection")
fun EditText.setSelection(str: String) {
    val textLength = if(str.isNotEmpty()) {
        str.length - CurrencyUtil.currency.length
    } else {
        0
    }
    Timber.e("checking ${textLength} ${CurrencyUtil.currency}")
    if(this.text.toString().isNotEmpty()) {
        this.setSelection(textLength)
    }
}

@BindingAdapter("bind:inputTypeBasedOnDecimal")
fun EditText.setInputTypeBasedOnDecimal(check: Boolean) {
    if (checkDecimalPoint()) {
        this.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    } else {
        this.inputType = InputType.TYPE_CLASS_NUMBER
    }
}