package com.aos.floney.ext

import android.text.Editable
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

    if(this.text.toString().isNotEmpty()) {
        this.setSelection(textLength)
    }
}