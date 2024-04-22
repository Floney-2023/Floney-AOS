package com.aos.model.analyze

import androidx.recyclerview.widget.DiffUtil

data class UiAnalyzeCategoryInComeModel(
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