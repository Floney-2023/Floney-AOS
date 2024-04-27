package com.aos.model.home

import androidx.recyclerview.widget.DiffUtil
import timber.log.Timber
import java.io.Serializable


data class UiBookDayModel(
    val data: List<DayMoney>,
    val extData: ExtData,
) {
    interface OnItemClickListener {
        fun onItemClick(item: DayMoney)
    }

    companion object : DiffUtil.ItemCallback<DayMoney>() {
        override fun areItemsTheSame(oldItem: DayMoney, newItem: DayMoney): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: DayMoney, newItem: DayMoney): Boolean {
            return oldItem == newItem
        }
    }
}

data class DayMoney(
    val id: Int,
    val money: String,
    val description: String,
    val lineCategory: String,
    val lineSubCategory: String,
    val assetSubCategory: String,
    val exceptStatus: Boolean,
    val writerEmail: String,
    val writerNickName: String,
    val writerProfileImg: String
)

// 내역 수정 전달 아이템
data class DayMoneyModifyItem(
    val id: Int,
    var money: String,
    val description: String,
    val lineDate: String,
    var lineCategory: String,
    val lineSubCategory: String,
    val assetSubCategory: String,
    val exceptStatus: Boolean,
    val writerNickName: String
): Serializable
