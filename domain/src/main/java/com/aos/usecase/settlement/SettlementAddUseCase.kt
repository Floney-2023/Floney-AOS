package com.aos.usecase.settlement
import com.aos.model.settlement.UiSettlementAddModel
import com.aos.model.settlement.settleOutcomes
import com.aos.repository.BookRepository
import javax.inject.Inject

class SettlementAddUseCase @Inject constructor(
    private val bookRepository: BookRepository
){

    suspend operator fun invoke(
        bookKey: String,
        startDate : String,
        endDate : String,
        usersEmails : List<String>,
        outcomes : List<settleOutcomes>
    ): Result<UiSettlementAddModel> {
        return bookRepository.postSettlementAdd(bookKey, startDate, endDate, usersEmails, outcomes)
    }

}