package com.aos.model.book

import androidx.recyclerview.widget.DiffUtil


data class UiBookCurrencyModel(
    val currencyList: List<Currency>
){
    interface OnItemClickListener {
        fun onItemClick(item: Currency)
    }

    companion object : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }
    }
}
data class Currency(
    val unit: String,
    val country: String,
    val isCheck : Boolean
)