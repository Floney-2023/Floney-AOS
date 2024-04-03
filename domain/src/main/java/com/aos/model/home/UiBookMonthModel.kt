package com.aos.model.home

import androidx.recyclerview.widget.DiffUtil
import timber.log.Timber


data class UiBookMonthModel(
    val data: List<ListData>,
    val extData: ExtData,
) {
    interface OnItemClickListener {
        fun onItemClick(item: ListData)
    }

    companion object : DiffUtil.ItemCallback<ListData>() {
        override fun areItemsTheSame(oldItem: ListData, newItem: ListData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ListData, newItem: ListData): Boolean {
            return oldItem == newItem
        }
    }
}

data class ListData(
    val date: String = "",
    val expenses: Expenses = Expenses(),
    val isToday: Boolean = false
)

data class ExtData(
    val totalIncome: String,
    val totalOutcome: String,
)

data class Expenses(
    val date: String = "",
    var outcome: String = "",
    val income: String = "",
)

