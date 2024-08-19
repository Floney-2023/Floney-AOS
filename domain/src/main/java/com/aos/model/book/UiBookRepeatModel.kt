package com.aos.model.book

import androidx.recyclerview.widget.DiffUtil


data class UiBookRepeatModel(
    val id: Int,
    var description: String,
    val repeatDuration: String,
    val lineSubCategory : String,
    val assetSubCategory : String,
    val money: String,
    val isDelete : Boolean = false
){
    interface OnItemClickListener {
        fun onItemClick(item: UiBookRepeatModel)
    }

    companion object : DiffUtil.ItemCallback<UiBookRepeatModel>() {
        override fun areItemsTheSame(oldItem: UiBookRepeatModel, newItem: UiBookRepeatModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: UiBookRepeatModel, newItem: UiBookRepeatModel): Boolean {
            return oldItem == newItem
        }
    }
}

