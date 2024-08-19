package com.aos.floney.ext

import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.aos.floney.base.BaseListAdapter
import com.aos.floney.base.BaseViewModel
import timber.log.Timber

@BindingAdapter(
    "items",
    "itemLayout",
    "diffCallback",
    "viewModel",
    "eventHolder",
    "headerItem",
    "headerItemLayout",
    requireAll = false
)
fun RecyclerView.bindSetAdapter(
    items: List<Any>? = null,
    @LayoutRes layoutResId: Int? = null,
    diffCallback: DiffUtil.ItemCallback<Any>? = null,
    viewModel: BaseViewModel? = null,
    eventHolder: Any? = null,
    headerItem: Any? = null,
    @LayoutRes headerLayoutResId: Int? = null,
) {
    val callback = diffCallback ?: return
    val baseListAdapter = this.adapter as? BaseListAdapter
        ?: BaseListAdapter(layoutResId, callback).also {
            it.eventHolder = eventHolder ?: viewModel
            this.adapter = it
        }

    baseListAdapter.headerItem = headerItem
    baseListAdapter.headerLayoutResId = headerLayoutResId

    baseListAdapter.submitList(items?.toList())

    // 깜빡임 애니메이션 효과 제거
    this.itemAnimator = null
}