package com.aos.floney.ext

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.aos.data.util.SharedPreferenceUtil
import com.aos.floney.R
import com.aos.model.analyze.UiAnalyzePlanModel
import com.bumptech.glide.Glide
import timber.log.Timber

@BindingAdapter("setImageToUrl")
fun ImageView.setImageToUrl(url: String?) {
    url?.let {
        if(it.equals("user_default")) {
            Glide.with(this)
                .load(R.drawable.icon_default_profile)
                .into(this)
        } else if(it.equals("book_default")) {
            Glide.with(this)
                .load(R.drawable.icon_book_profile)
                .into(this)
        } else if(it.equals("user_book_default")) {
            Glide.with(this)
                .load(R.drawable.btn_profile)
                .into(this)
        } else if(it.equals("btn_book_default")) {
            Glide.with(this)
                .load(R.drawable.btn_book_profile)
                .into(this)
        } else {
            Glide.with(this)
                .load(url)
                .into(this)
        }
    }
}


@BindingAdapter("setBookImageToUrl")
fun ImageView.setBookImageToUrl(url: String?) {
    url?.let {
        if(it.equals("") || it == null) {
            Glide.with(this)
                .load(R.drawable.icon_book_profile)
                .into(this)
        } else if(it.equals("btn_book_default")) {
            Glide.with(this)
                .load(R.drawable.btn_book_profile)
                .into(this)
        } else {
            Glide.with(this)
                .load(url)
                .into(this)
        }
    }
}
@BindingAdapter("setUserImageToUrl")
fun ImageView.setUserImageToUrl(url: String?) {
    url?.let {
        val prefs = SharedPreferenceUtil(context)

        if(!prefs.getBoolean("seeProfileStatus",true) || it.equals("user_default")) {
            Glide.with(this)
                .load(R.drawable.icon_default_profile)
                .into(this)
        } else if(it.equals("user_btn_default")) {
            Glide.with(this)
                .load(R.drawable.btn_profile)
                .into(this)
        } else {
            Glide.with(this)
                .load(url)
                .into(this)
        }
    }
}

@BindingAdapter("setAlarmImage")
fun ImageView.setAlarmImage(url: String?) {
    url?.let {
        if(it.equals("icon_noti_currency")) {
            Glide.with(this)
                .load(R.drawable.icon_alarm_currrency)
                .into(this)
        } else if(it.equals("icon_noti_exit")) {
            Glide.with(this)
                .load(R.drawable.icon_noti_exit)
                .into(this)
        } else if(it.equals("icon_noti_join")) {
            Glide.with(this)
                .load(R.drawable.icon_noti_join)
                .into(this)
        } else if(it.equals("icon_noti_reset")) {
            Glide.with(this)
                .load(R.drawable.icon_noti_reset)
                .into(this)
        } else if(it.equals("icon_noti_settlement")) {
            Glide.with(this)
                .load(R.drawable.icon_noti_settlement)
                .into(this)
        }else {
            Glide.with(this)
                .load(url)
                .into(this)
        }
    }
}

@BindingAdapter("setAnalyzeImage")
fun ImageView.setAnalyzeImage(item: UiAnalyzePlanModel?) {

    if(item != null) {
        if(item.initBudget.substring(0, item.initBudget.length - 1) == "0") {
            Glide.with(this)
                .load(R.drawable.analyze_plan_0_49_icon)
                .into(this)
        } else {
            when(item.percent.toInt()) {
                in 0..30 ->{
                    Glide.with(this)
                        .load(R.drawable.analyze_plan_0_49_icon)
                        .into(this)
                }
                in 31..60 -> {
                    Glide.with(this)
                        .load(R.drawable.analyze_plan_50_79_icon)
                        .into(this)
                }
                in 61..100 -> {
                    Glide.with(this)
                        .load(R.drawable.analyze_plan_80_99_icon)
                        .into(this)
                }
            }
        }
    } else {
        Glide.with(this)
            .load(R.drawable.analyze_plan_0_49_icon)
            .into(this)
    }
}

@BindingAdapter("bind:setImageColor")
fun ImageView.setImageColor(color: Int) {
    this.setBackgroundColor(color)
}