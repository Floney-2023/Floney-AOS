package com.aos.model.analyze

import androidx.recyclerview.widget.DiffUtil

data class UiAnalyzeCategoryOutComeModel(
    val total: String,
    val differance: String,
    val size: Int,
    val analyzeResult: List<AnalyzeResult>
) {

    interface OnItemClickListener {
        fun onItemClick(item: AnalyzeResult)
    }

    companion object : DiffUtil.ItemCallback<AnalyzeResult>() {
        override fun areItemsTheSame(oldItem: AnalyzeResult, newItem: AnalyzeResult): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: AnalyzeResult, newItem: AnalyzeResult): Boolean {
            return oldItem == newItem
        }
    }
}

data class AnalyzeResult(
    val category: String,
    val money: Double,
    val uiMoney: String,
    val percent: Double,
    val uiPercent: String,
    val color: Int
)