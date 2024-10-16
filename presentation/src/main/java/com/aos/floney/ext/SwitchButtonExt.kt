package com.aos.floney.ext

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.suke.widget.SwitchButton


@BindingAdapter("bind:sb_checked")
fun SwitchButton.setChecked(isChecked: Boolean) {
    this.isChecked = isChecked
}

@BindingAdapter("app:onCheckedChanged")
fun setOnCheckedChangeListener(
    switchButton: SwitchButton,
    listener: ((Boolean) -> Unit)?
) {
    if (listener != null) {
        switchButton.setOnCheckedChangeListener { _, isChecked ->
            listener(isChecked)
        }
    } else {
        switchButton.setOnCheckedChangeListener(null)
    }
}