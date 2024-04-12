package com.aos.floney.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import timber.log.Timber
import java.text.DecimalFormat
import java.text.NumberFormat

fun String.formatNumber(): String {
     return if(this != "") {
        Timber.e("this $this")
        DecimalFormat("###,###").format(this.replace(",", "").toLong())
    } else {
        ""
    }
}