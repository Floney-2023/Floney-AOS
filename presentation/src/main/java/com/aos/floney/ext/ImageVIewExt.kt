package com.aos.floney.ext

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.aos.floney.R
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

@BindingAdapter("bind:setImageColor")
fun ImageView.setImageColor(color: Int) {
    this.setBackgroundColor(color)
}