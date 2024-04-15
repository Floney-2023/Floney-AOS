package com.aos.floney.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.aos.floney.R
import com.bumptech.glide.Glide
import timber.log.Timber

@BindingAdapter("setImageToUrl")
fun ImageView.setImageToUrl(url: String?) {
    url?.let {
        if(it != "user_default") {
            Glide.with(this)
                .load(url)
                .into(this)
        } else {
            Glide.with(this)
                .load(R.drawable.icon_default_profile)
                .into(this)
        }

    }

}