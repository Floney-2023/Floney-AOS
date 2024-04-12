package com.aos.model.book

import androidx.recyclerview.widget.DiffUtil
import timber.log.Timber

data class UiBookCategory(
    val idx: Int,
    var checked: Boolean = false,
    val name: String,
    val default: Boolean
){
    interface OnItemClickListener {
        fun onItemClick(item: UiBookCategory)
    }

    companion object : DiffUtil.ItemCallback<UiBookCategory>() {
        override fun areItemsTheSame(oldItem: UiBookCategory, newItem: UiBookCategory): Boolean {
            Timber.e("oldItem.hashCode() ${oldItem.hashCode()}")
            Timber.e("newItem.hashCode() ${newItem.hashCode()}")
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: UiBookCategory, newItem: UiBookCategory): Boolean {
            return oldItem == newItem
        }
    }
}

