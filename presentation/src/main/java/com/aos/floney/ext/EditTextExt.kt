package com.aos.floney.ext

import android.text.Editable
import android.text.InputType
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.aos.data.util.CurrencyUtil
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
fun EditText.setInputTypeBasedOnDecimal(allowDecimal: Boolean) {
    if (allowDecimal) {
        this.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    } else {
        this.inputType = InputType.TYPE_CLASS_NUMBER
    }
}