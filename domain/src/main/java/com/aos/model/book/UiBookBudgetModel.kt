package com.aos.model.book

import androidx.recyclerview.widget.DiffUtil


data class UiBookBudgetModel(
    val budgetList: List<BudgetItem>
){
    interface OnItemClickListener {
        fun onItemClick(item: BudgetItem)
    }

    companion object : DiffUtil.ItemCallback<BudgetItem>() {
        override fun areItemsTheSame(oldItem: BudgetItem, newItem: BudgetItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: BudgetItem, newItem: BudgetItem): Boolean {
            return oldItem == newItem
        }
    }
}
data class BudgetItem(
    val month: String,
    val money: String,
    val isExist: Boolean
)