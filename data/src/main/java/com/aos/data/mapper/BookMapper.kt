package com.aos.data.mapper

import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.model.home.Expenses
import com.aos.model.home.ExtData
import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.ListData
import com.aos.model.home.UiBookMonthModel
import timber.log.Timber
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// 유저 가계부 유효 확인
fun GetCheckUserBookEntity.toGetCheckUserBookModel(): GetCheckUserBookModel {
    return GetCheckUserBookModel(this.bookKey ?: "")
}

// 캘린더 조회
fun GetBookMonthEntity.toUiBookMonthModel(): UiBookMonthModel {
    val listData = arrayListOf<ListData>()
    var tempDay = ""
    var tempExpenses: Expenses? = null
    var tempDate = ""
    var tempOutcome = ""

    val calendar = Calendar.getInstance()
    val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)

    this.expenses.forEach {
        if (it.date == tempDate) {
            tempExpenses?.outcome = tempOutcome
            listData.add(
                ListData(
                    tempDay, tempExpenses!!, todayDate == it.date
                )
            )
        } else {
            tempDate = it.date
            tempOutcome = "-${NumberFormat.getNumberInstance().format(it.money)}"
            tempDay = it.date.split("-")[2].toInt().toString()
            tempExpenses = Expenses(
                date = it.date,
                outcome = tempOutcome,
                income = if (it.categoryType == "INCOME") {
                    "+${NumberFormat.getNumberInstance().format(it.money)}"
                } else {
                    ""
                }
            )
        }
    }

    // 날짜 앞 빈공백 추가
    val year = tempDate.split("-")[0].toInt()
    val month = (tempDate.split("-")[1].toInt() - 1)

    calendar.set(year, month, 1)

    // 달의 첫번째 요일 저장
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val list = arrayListOf<ListData>()

    for (i in 2..firstDayOfWeek) {
        list.add(ListData())
    }
    list.addAll(listData)

    return if (carryOverInfo.carryOverStatus) {
        if(carryOverInfo.carryOverMoney >= 0) {
            // 총 수입에 포함
            UiBookMonthModel(
                list, ExtData(
                    "${NumberFormat.getNumberInstance().format(totalIncome + carryOverInfo.carryOverMoney)}원",
                    "${NumberFormat.getNumberInstance().format(totalOutcome)}원"
                )
            )
        } else {
            // 총 지출에 포함
            UiBookMonthModel(
                list, ExtData(
                    "${NumberFormat.getNumberInstance().format(totalIncome)}원",
                    "${NumberFormat.getNumberInstance().format(totalOutcome + carryOverInfo.carryOverMoney)}원"
                )
            )
        }
    } else {
        // 이월 내역 없을 경우
        UiBookMonthModel(
            list, ExtData(
                "${NumberFormat.getNumberInstance().format(totalIncome)}원",
                "${NumberFormat.getNumberInstance().format(totalOutcome)}원"
            )
        )
    }
}
