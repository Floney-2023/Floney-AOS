package com.aos.floney.ext

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.aos.floney.R
import timber.log.Timber
import java.text.DecimalFormat
import java.text.NumberFormat

@BindingAdapter("app:fontFamily")
fun setFontFamily(textView: TextView, isCheck: Boolean) {
    val context = textView.context
    val typeface = if (isCheck) {
        ResourcesCompat.getFont(context, R.font.pretendard_bold)
    } else {
        ResourcesCompat.getFont(context, R.font.pretendard_regular)
    }
    textView.typeface = typeface
}