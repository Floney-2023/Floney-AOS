package com.aos.floney.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import timber.log.Timber
import java.text.DecimalFormat
import java.text.NumberFormat

fun String.formatNumber(): String {
     return if(this != "") {
         val text = this.replace("원", "")
         if(text != "") {
             DecimalFormat("###,###").format(text.replace(",", "").toLong())
         } else {
             ""
         }
    } else {
        ""
    }
}