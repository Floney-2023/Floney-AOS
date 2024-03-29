package com.aos.floney.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImageToUrl")
fun ImageView.setImageToUrl(url: String?) {
    url?.let {
        Glide.with(this)
            .load(url)
            .into(this)
    }

}