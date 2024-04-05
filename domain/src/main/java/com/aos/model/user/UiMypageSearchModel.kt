package com.aos.model.user

import androidx.recyclerview.widget.DiffUtil

data class UiMypageSearchModel(
    val nickname: String,
    val email: String,
    val profileImg: String,
    val provider: String,
    val lastAdTime: String,
    val myBooks: List<MyBooks>
) {
    interface OnItemClickListener {
        fun onItemClick(item: MyBooks)
    }

    companion object : DiffUtil.ItemCallback<MyBooks>() {
        override fun areItemsTheSame(oldItem: MyBooks, newItem: MyBooks): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: MyBooks, newItem: MyBooks): Boolean {
            return oldItem == newItem
        }
    }
}
data class MyBooks(
    val bookImg: String?,
    val bookKey: String,
    val name: String,
    val memberCount: Int
)