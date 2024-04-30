package com.aos.model.alarm

import androidx.recyclerview.widget.DiffUtil


data class UiAlarmGetModel(
    val id: Int,
    val title: String,
    val body: String,
    val imgUrl: String,
    val date: String,
    val received: Boolean
){
    interface OnItemClickListener {
        fun onItemClick(item: UiAlarmGetModel)
    }

    companion object : DiffUtil.ItemCallback<UiAlarmGetModel>() {
        override fun areItemsTheSame(oldItem: UiAlarmGetModel, newItem: UiAlarmGetModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: UiAlarmGetModel, newItem: UiAlarmGetModel): Boolean {
            return oldItem == newItem
        }
    }
}