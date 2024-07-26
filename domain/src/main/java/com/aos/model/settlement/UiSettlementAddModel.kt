package com.aos.model.settlement

import androidx.recyclerview.widget.DiffUtil
import retrofit2.http.POST

data class UiSettlementAddModel(
    val id: Long,
    val startDate : String,
    val endDate : String,
    val dateString : String,
    val userCount : Int,
    val totalOutcome : String,
    val outcome : String,
    val details : List<Details>,
    val expenses: List<Expenses>
){
    interface OnItemClickListener {
        fun onItemClick(item: Details)
    }

    companion object : DiffUtil.ItemCallback<Details>() {
        override fun areItemsTheSame(oldItem: Details, newItem: Details): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Details, newItem: Details): Boolean {
            return oldItem == newItem
        }
    }
}
data class Details(
    val money: String,
    val userNickname: String,
    val useruserProfileImg : String,
    val moneyInfo : String
)

data class Expenses(
    val money: String,
    val userNickname: String
)