package com.aos.model.settlement

import androidx.recyclerview.widget.DiffUtil


data class UiPeriodSelectModel(
    val data: List<PeriodCalendar>
) {
    interface OnItemClickListener {
        fun onItemClick(item: PeriodCalendar)
    }

    companion object : DiffUtil.ItemCallback<PeriodCalendar>() {
        override fun areItemsTheSame(oldItem: PeriodCalendar, newItem: PeriodCalendar): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: PeriodCalendar, newItem: PeriodCalendar): Boolean {
            return oldItem == newItem
        }
    }
}

data class PeriodCalendar(
    val year: Int,
    val month: Int,
    val day: Int,
    val isMonth : Boolean,
    val isClick: Boolean,
    val isRange: Boolean
)
