package com.aos.model.user

data class DeleteWithdrawModel(
    val deletedBookKeys: List<String> = emptyList(),
    val otherBookKeys: List<String> = emptyList()
)
