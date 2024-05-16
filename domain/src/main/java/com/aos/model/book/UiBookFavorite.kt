package com.aos.model.book

import androidx.recyclerview.widget.DiffUtil
import timber.log.Timber


data class UiBookFavorite(
    val idx: Int,
    var checked: Boolean = false,
    val description: String,
    val info: String,
    val money: String
){
    interface OnItemClickListener {
        fun onItemClick(item: UiBookFavorite)
    }

    companion object : DiffUtil.ItemCallback<UiBookFavorite>() {
        override fun areItemsTheSame(oldItem: UiBookFavorite, newItem: UiBookFavorite): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: UiBookFavorite, newItem: UiBookFavorite): Boolean {
            return oldItem == newItem
        }
    }
}

