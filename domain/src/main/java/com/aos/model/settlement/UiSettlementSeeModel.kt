package com.aos.model.settlement

import androidx.recyclerview.widget.DiffUtil


data class UiSettlementSeeModel(
    val settlementList: List<Settlement>
) {
    interface OnItemClickListener {
        fun onItemClick(item: Settlement)
    }

    companion object : DiffUtil.ItemCallback<Settlement>() {
        override fun areItemsTheSame(oldItem: Settlement, newItem: Settlement): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Settlement, newItem: Settlement): Boolean {
            return oldItem == newItem
        }
    }
}
data class Settlement(
    val id: Long,
    val dateString : String,
    val userCount: String,
    val totalOutcome : String,
    val outcome : String
)
