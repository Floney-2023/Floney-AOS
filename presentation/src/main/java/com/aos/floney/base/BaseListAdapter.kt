package com.aos.floney.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import timber.log.Timber

open class BaseListAdapter(
    @LayoutRes private val layoutResId: Int?,
    diffCallback: DiffUtil.ItemCallback<Any>,
) : ListAdapter<Any, BaseViewHolder>(diffCallback) {
    var eventHolder: Any? = null

    private var headerSize: Int = 0

    @LayoutRes
    var headerLayoutResId: Int? = null
        set(value) {
            if (value == field) {
                return
            }
            field = value
            headerSize = if (value == null) 0 else 1
        }
    var headerItem: Any? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        layoutResId ?: error("layoutResId is null. It cannot be null!")
        return BaseViewHolder(layoutResId = viewType, parent = parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = when {
            isHeaderPosition(position) -> headerItem
            else -> getItem(position - headerSize)
        }
        holder.onBind(item, eventHolder)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + headerSize
    }

    override fun getItemViewType(position: Int): Int {
        if (isHeaderPosition(position)) {
            return headerLayoutResId!!
        }
        return when (val item = getItem(position - headerSize)) {
            is BaseItemViewType -> item.itemLayoutResId
            else -> layoutResId ?: error("layoutResId is null. It cannot be null!")
        }
    }

    private fun isHeaderPosition(position: Int) = headerSize != 0 && position == 0

}