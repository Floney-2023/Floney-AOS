package com.aos.floney.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.aos.data.util.CurrencyUtil
import com.aos.model.analyze.Asset
import com.aos.model.analyze.UiAnalyzeAssetModel
import com.aos.model.analyze.UiAnalyzePlanModel
import timber.log.Timber
import java.text.DecimalFormat
import java.text.NumberFormat

fun String.formatNumber(): String {
     return if(this != "") {
         val text = this.replace("${CurrencyUtil.currency}", "")
         if(text != "") {
             DecimalFormat("###,###").format(text.replace(",", "").toLong())
         } else {
             ""
         }
    } else {
        ""
    }
}

@BindingAdapter("bind:setPlanText")
fun TextView.setPlanText(item: UiAnalyzePlanModel?) {

    if(item != null) {
        if(item.initBudget.substring(0, item.initBudget.length - 1) == "0") {
            this.text = "예산을 설정하고\n체계적인 소비 습관을 만들어 보세요!"
        } else {
            when(item.percent.toInt()) {
                in 0..49 -> this.text = "쓸 수 있는 예산이\n충분해요!"
                in 50..79 -> this.text = "조금씩 지출을\n줄여볼까요?"
                in 80..99 -> this.text = "예산을 넘기지 않게\n주의하세요!"
            }
        }
    } else {
        this.text = ""
    }
}

@BindingAdapter("bind:setAssetMonthText")
fun TextView.setAssetMonthText(item: Asset?){
    if(item != null) {
        this.text = item.month.toString()
    } else {
        this.text = ""
    }
}