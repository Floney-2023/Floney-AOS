package com.aos.data.mapper

import com.aos.data.entity.response.book.GetBookCategoryEntity
import com.aos.data.entity.response.book.PostBooksCreateEntity
import com.aos.data.entity.response.book.PostBooksJoinEntity
import com.aos.data.entity.response.home.GetBookDaysEntity
import com.aos.data.entity.response.home.GetBookInfoEntity
import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.UiBookCategory
import com.aos.model.home.DayMoney
import com.aos.model.home.Expenses
import com.aos.model.home.ExtData
import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.MonthMoney
import com.aos.model.home.OurBookUsers
import com.aos.model.home.UiBookDayModel
import com.aos.model.home.UiBookInfoModel
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
    val listData = arrayListOf<MonthMoney>()
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
                MonthMoney(
                    "${it.date.split("-")[0].toInt()}",
                    "${it.date.split("-")[1].toInt()}",
                    "${it.date.split("-")[2].toInt()}",
                    Expenses(
                        date = it.date,
                        outcome = if (!it.money.toString().equals("0.0")) {
                            "-${NumberFormat.getNumberInstance().format(it.money)}"
                        } else {
                            ""
                        },
                        income = tempExpenses!!.income
                    ),
                    todayDate == it.date
                )
            )
        } else {
            tempDate = it.date
            tempOutcome = "-${NumberFormat.getNumberInstance().format(it.money)}"
            tempDay = it.date.split("-")[2].toInt().toString()
            tempExpenses = Expenses(
                date = it.date,
                outcome = tempOutcome,
                income = if (!it.money.toString().equals("0.0")) {
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
    val list = arrayListOf<MonthMoney>()

    for (i in 2..firstDayOfWeek) {
        list.add(MonthMoney())
    }
    list.addAll(listData)

    return if (carryOverInfo.carryOverStatus) {
        if (carryOverInfo.carryOverMoney >= 0) {
            // 총 수입에 포함
            UiBookMonthModel(
                list, ExtData(
                    "${
                        NumberFormat.getNumberInstance()
                            .format(totalIncome + carryOverInfo.carryOverMoney)
                    }원", "${NumberFormat.getNumberInstance().format(totalOutcome)}원"
                )
            )
        } else {
            // 총 지출에 포함
            UiBookMonthModel(
                list, ExtData(
                    "${NumberFormat.getNumberInstance().format(totalIncome)}원", "${
                        NumberFormat.getNumberInstance()
                            .format(totalOutcome + carryOverInfo.carryOverMoney)
                    }원"
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

// 일별 조회
fun GetBookDaysEntity.toUiBookMonthModel(): UiBookDayModel {
    val dayMoneyList: List<DayMoney> = this.dayLinesResponse.map {
        DayMoney(
            money = if (it.lineCategory == "INCOME") {
                "+ ${NumberFormat.getNumberInstance().format(it.money)}"
            } else {
                "- ${NumberFormat.getNumberInstance().format(it.money)}"
            },
            description = it.description,
            lineCategory = it.lineCategory,
            lineSubCategory = it.lineSubCategory,
            assetSubCategory = it.assetSubCategory,
            exceptStatus = it.exceptStatus,
            writerEmail = it.writerEmail,
            writerNickName = it.writerNickname,
            writerProfileImg = it.writerProfileImg
        )
    }

    Timber.e("this.dayLiensResponse ${this.dayLinesResponse}")
    Timber.e("dayMoneyList $dayMoneyList")

    var totalIncome = 0
    var totalOutcome = 0

    this.totalExpense.forEach {
        if (it.categoryType == "INCOME") {
            totalIncome = it.money.toInt()
        }
        if (it.categoryType == "OUTCOME") {
            totalOutcome = it.money.toInt()
        }
    }

    return if (carryOverInfo.carryOverStatus) {
        if (carryOverInfo.carryOverMoney >= 0) {
            // 총 수입에 포함
            UiBookDayModel(
                dayMoneyList, ExtData(
                    "${
                        NumberFormat.getNumberInstance()
                            .format(totalIncome + carryOverInfo.carryOverMoney)
                    }원", "${NumberFormat.getNumberInstance().format(totalOutcome)}원"
                )
            )
        } else {
            // 총 지출에 포함
            UiBookDayModel(
                dayMoneyList, ExtData(
                    "${NumberFormat.getNumberInstance().format(totalIncome)}원", "${
                        NumberFormat.getNumberInstance()
                            .format(totalOutcome + carryOverInfo.carryOverMoney)
                    }원"
                )
            )
        }
    } else {
        // 이월 내역 없을 경우
        UiBookDayModel(
            dayMoneyList, ExtData(
                "${NumberFormat.getNumberInstance().format(totalIncome)}원",
                "${NumberFormat.getNumberInstance().format(totalOutcome)}원"
            )
        )
    }
}

fun GetBookInfoEntity.toUiBookInfoModel(): UiBookInfoModel {
    val ourBookUsers = this.ourBookUsers.map {
        OurBookUsers(
            name = it.name, profileImg = it.profileImg, role = it.role, me = it.me
        )
    }
    return UiBookInfoModel(
        bookName = this.bookName,
        bookImg = this.bookImg,
        startDay = this.startDay,
        seeProfileStatus = this.seeProfileStatus,
        carryOver = this.carryOver,
        ourBookUsers = ourBookUsers
    )
}


fun PostBooksJoinEntity.toPostBooksJoinModel(): PostBooksJoinModel {
    return PostBooksJoinModel(this.bookKey ?: "", this.code ?: "")
}

fun PostBooksCreateEntity.toPostBooksCreateModel(): PostBooksCreateModel {
    return PostBooksCreateModel(this.bookKey ?: "", this.code ?: "")
}

fun List<GetBookCategoryEntity>.toUiBookCategory(): List<UiBookCategory> {
    return this.map {
        UiBookCategory(
            it.name, it.default
        )
    }
}