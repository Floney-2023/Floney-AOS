package com.aos.data.mapper

import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.model.home.CarryOverInfo
import com.aos.model.home.Expenses
import com.aos.model.home.ExtData
import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.ListData
import com.aos.model.home.UiBookMonthModel
import timber.log.Timber
import java.text.NumberFormat

fun GetCheckUserBookEntity.toGetCheckUserBookModel(): GetCheckUserBookModel {
    return GetCheckUserBookModel(this.bookKey ?: "")
}

fun GetBookMonthEntity.toUiBookMonthModel(): UiBookMonthModel {
    val listData = arrayListOf<ListData>()
    var tempDay = ""
    var tempExpenses: Expenses? = null
    var tempDate = ""
    var tempOutcome = ""

    this.expenses.forEach {
        if(it.date == tempDate) {
            tempExpenses?.outcome = tempOutcome
            listData.add(
                ListData(
                    tempDay,
                    tempExpenses!!,
                )
            )
        } else {
            tempDate = it.date
            tempOutcome = "-${NumberFormat.getNumberInstance().format(it.money)}"
            tempDay = it.date.split("-")[2].toInt().toString()
            tempExpenses = Expenses(
                year = "${it.date.split("-")[0]}년",
                month = "${it.date.split("-")[1]}월",
                day = "${it.date.split("-")[2]}일",
                outcome = tempOutcome,
                income = if (it.categoryType == "INCOME") {
                    "+${NumberFormat.getNumberInstance().format(it.money)}"
                } else {
                    ""
                }
            )
        }
    }

    val carryOverInfo = CarryOverInfo(
        this.carryOverInfo.carryOverStatus,
        this.carryOverInfo.carryOverMoney,
    )

    return UiBookMonthModel(
        listData,
        ExtData(
            totalIncome,
            totalOutcome,
            carryOverInfo
        )
    )
}
