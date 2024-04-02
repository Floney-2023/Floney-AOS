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
            Timber.e("oldItem $oldItem")
            Timber.e("newItem $newItem")
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ListData, newItem: ListData): Boolean {
            return oldItem == newItem
        }
    }
}

data class ListData(
    val date: String,
    val expenses: Expenses,
)

data class ExtData(
    val totalIncome: Double,
    val totalOutcome: Double,
    val carryOverInfo: CarryOverInfo
)

data class Expenses(
    val year: String,
    val month: String,
    val day: String,
    var outcome: String,
    val income: String,
)

data class CarryOverInfo(
    val carryOverStatus: Boolean,
    val carryOverMoney: Double
)

