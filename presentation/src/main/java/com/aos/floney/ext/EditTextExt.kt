package com.aos.floney.ext

import android.text.Editable
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:setSelection")
fun EditText.setSelection(str: String) {
    val textLength = if(str.isNotEmpty()) {
        str.length - 1
    } else {
        0
    }
    this.setSelection(textLength)
}