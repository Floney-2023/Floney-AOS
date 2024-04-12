package com.aos.model.settlement

import androidx.recyclerview.widget.DiffUtil


data class UiOutcomesSelectModel(
    val outcomes: List<Outcomes>
) {
    interface OnItemClickListener {
        fun onItemClick(item: Outcomes)
    }

    companion object : DiffUtil.ItemCallback<Outcomes>() {
        override fun areItemsTheSame(oldItem: Outcomes, newItem: Outcomes): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Outcomes, newItem: Outcomes): Boolean {
            return oldItem == newItem
        }
    }
}
data class Outcomes(
    val id: Int?,
    val money: Long,
    val category: String,
    val assetType: String,
    val content: String,
    val img: String,
    val userEmail: String,
    val isClick : Boolean
)
data class settleOutcomes(
    val outcome: Long,
    val userEmail : String
)
