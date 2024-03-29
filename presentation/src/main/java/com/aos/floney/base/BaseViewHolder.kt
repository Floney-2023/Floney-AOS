package com.aos.floney.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(
    @LayoutRes layoutResId: Int,
    parent: ViewGroup?,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent?.context)
        .inflate(layoutResId, parent, false)
), LifecycleOwner {
    protected val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!

    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    fun onBind(item: Any?, eventHolder: Any?) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.eventHolder, eventHolder)
        binding.executePendingBindings()
    }

    fun onAttach() {
        lifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    fun onDetach() {
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

}