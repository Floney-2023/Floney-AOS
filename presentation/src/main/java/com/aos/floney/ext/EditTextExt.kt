package com.aos.floney.ext

import android.text.Editable
import android.widget.EditText
import androidx.databinding.BindingAdapter
import timber.log.Timber

@BindingAdapter("bind:setSelection")
fun EditText.setSelection(str: String) {
    val textLength = if(str.isNotEmpty()) {
        str.length - 1
    } else {
        0
    }

    if(this.text.toString().isNotEmpty()) {
        this.setSelection(textLength)
    }
}