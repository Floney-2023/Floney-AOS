package com.aos.model.settlement

import androidx.recyclerview.widget.DiffUtil


data class UiMemberSelectModel(
    val booksUsers: List<BookUsers>
) {
    interface OnItemClickListener {
        fun onItemClick(item: BookUsers)
    }

    companion object : DiffUtil.ItemCallback<BookUsers>() {
        override fun areItemsTheSame(oldItem: BookUsers, newItem: BookUsers): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: BookUsers, newItem: BookUsers): Boolean {
            return oldItem == newItem
        }
    }
}
data class BookUsers(
    val email: String,
    val nickname: String,
    val profileImg: String,
    val isCheck: Boolean
)
