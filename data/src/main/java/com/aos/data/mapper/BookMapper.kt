package com.aos.data.mapper

import com.aos.data.entity.request.book.PostBooksLinesEntity
import com.aos.data.entity.response.book.GetBookCategoryEntity
import com.aos.data.entity.response.book.GetBookRepeatEntity
import com.aos.data.entity.response.book.PostBooksChangeEntity
import com.aos.data.entity.response.book.GetBooksBudgetEntity
import com.aos.data.entity.response.book.GetBooksCodeEntity
import com.aos.data.entity.response.book.GetBooksInfoCurrencyEntity
import com.aos.data.entity.response.book.GetBooksInfoEntity
import com.aos.data.entity.response.book.PostBooksCategoryAddEntity
import com.aos.data.entity.response.book.PostBooksCreateEntity
import com.aos.data.entity.response.book.PostBooksInfoCurrencyEntity
import com.aos.data.entity.response.book.PostBooksJoinEntity
import com.aos.data.entity.response.home.GetBookDaysEntity
import com.aos.data.entity.response.home.GetBookInfoEntity
import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.model.book.PostBooksChangeModel
import com.aos.data.entity.response.settlement.GetBooksUsersEntity
import com.aos.data.entity.response.settlement.GetSettleUpLastEntity
import com.aos.data.entity.response.settlement.GetSettlementSeeEntity
import com.aos.data.entity.response.settlement.PostBooksOutcomesEntity
import com.aos.data.entity.response.settlement.PostSettlementAddEntity
import com.aos.model.book.BudgetItem
import com.aos.model.book.GetBooksCodeModel
import com.aos.model.book.GetBooksInfoCurrencyModel
import com.aos.model.book.MyBookUsers
import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksInfoCurrencyModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.PostBooksLinesModel
import com.aos.model.book.UiBookBudgetModel
import com.aos.model.book.UiBookCategory
import com.aos.model.book.UiBookSettingModel
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
import com.aos.model.settlement.BookUsers
import com.aos.model.settlement.Details
import com.aos.model.settlement.GetSettlementLastModel
import com.aos.model.settlement.Outcomes
import com.aos.model.settlement.Settlement
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiOutcomesSelectModel
import com.aos.model.settlement.UiSettlementAddModel
import com.aos.model.settlement.UiSettlementSeeModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.absoluteValue
import kotlin.math.roundToLong

import com.aos.model.book.PostBooksCategoryAddModel
import com.aos.model.book.UiBookRepeatModel

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
                        date = it.date, outcome = if (!it.money.toString().equals("0.0")) {
                            "-${NumberFormat.getNumberInstance().format(it.money)}"
                        } else {
                            ""
                        }, income = tempExpenses!!.income
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
            id = it.id,
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
        bookImg = this.bookImg?:"book_default",
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

fun PostBooksLinesEntity.toPostBooksLinesModel(): PostBooksLinesModel {
    return PostBooksLinesModel(
        money = this.money.toInt(),
        flow = this.flow,
        asset = this.asset,
        line = this.line,
        lineDate = this.lineDate,
        description = this.description,
        except = this.except,
        nickname = this.nickname,
        repeatDuration = this.repeatDuration
    )
}

fun PostBooksChangeEntity.toPostBooksLinesChangeModel(): PostBooksChangeModel {
    return PostBooksChangeModel(
        money = this.money,
        flow = this.flow,
        asset = this.asset,
        line = this.line,
        lineDate = this.lineDate,
        description = this.description,
        except = this.except,
        nickname = this.nickname
    )
}

fun List<GetBookCategoryEntity>.toUiBookCategory(): List<UiBookCategory> {
    var idx = 0
    return this.map {
        UiBookCategory(
            idx++, false, it.name, it.default
        )
    }
}

fun GetSettleUpLastEntity.toGetsettleUpLastModel() : GetSettlementLastModel {
    return GetSettlementLastModel(passedDays = this.passedDays)
}

fun List<GetBooksUsersEntity>.toUiMemberSelectModel(): UiMemberSelectModel {
    val myBookUsers = this.map {
        BookUsers(
            email = it.email, nickname = it.nickname, profileImg = it.profileImg, isCheck = false
        )
    }
    return UiMemberSelectModel(
        booksUsers = myBookUsers
    )
}
fun List<PostBooksOutcomesEntity>.toUiOutcomesSelectModel(): UiOutcomesSelectModel {
    val myBookOutcomes = this.mapIndexed { index, item ->
        Outcomes(
            id = index, // 인덱스를 id로 사용
            money = item.money.roundToLong(),
            moneyFormat ="${NumberFormat.getNumberInstance().format(item.money.roundToLong())}원" ,
            category = "${item.category[0]} ‧ ${item.category[1]}",
            assetType = item.assetType,
            content = item.content,
            img = item.img,
            userEmail = item.userEmail,
            isClick = false
        )
    }
    return UiOutcomesSelectModel(
        outcomes = myBookOutcomes
    )
}
fun PostSettlementAddEntity.toPostSettlementAddModel(): UiSettlementAddModel {

    val details = this.details.map {
        Details(
            money = if (it.money.toInt() == 0 ) "" else "${NumberFormat.getNumberInstance().format(it.money.roundToLong().absoluteValue)}원",
            userNickname = it.userNickname,
            useruserProfileImg = it.userProfileImg,
            moneyInfo = if (it.money > 0) "을 보내야해요." else if (it.money < 0) "을 받아야해요." else "정산할 금액이 없어요."
        )
    }
    return UiSettlementAddModel(
        id = this.id,
        startDate = this.startDate,
        endDate = this.endDate,
        dateString = "${this.startDate.replace('-','.')} - ${this.endDate.replace('-','.')}",
        userCount = this.userCount,
        totalOutcome ="${NumberFormat.getNumberInstance().format(this.totalOutcome.roundToLong())}원",
        outcome = "${NumberFormat.getNumberInstance().format(this.outcome.roundToLong())}원",
        details = details
    )
}
fun List<GetSettlementSeeEntity>.toUiSettlementSeeModel(): UiSettlementSeeModel {
    val myBookSettlements = this.map {
        Settlement(
            id = it.id,
            dateString = "${it.startDate.replace('-','.')} - ${it.endDate.replace('-','.')}",
            userCount = "${it.userCount}명",
            totalOutcome = "${NumberFormat.getNumberInstance().format(it.totalOutcome)}원",
            outcome = "${NumberFormat.getNumberInstance().format(it.outcome)}원"
        )
    }
    return UiSettlementSeeModel(
        settlementList = myBookSettlements
    )
}

fun GetBooksInfoEntity.toUiBookSettingModel(): UiBookSettingModel {
    val myBookUsers = this.ourBookUsers.map {
        val roleString = if (it.me) "${it.role}·나" else it.role
        MyBookUsers(
            name = it.name, profileImg = it.profileImg, role = roleString, me = it.me
        )
    }
    return UiBookSettingModel(
        bookName = this.bookName,
        bookImg = this.bookImg?:"book_default",
        startDay = "${this.startDay.replace('-','.')} 개설",
        seeProfileStatus = this.seeProfileStatus,
        carryOver = this.carryOver,
        ourBookUsers = myBookUsers
    )
}

fun PostBooksInfoCurrencyEntity.toPostBooksInfoCurrencyModel() : PostBooksInfoCurrencyModel {
    return PostBooksInfoCurrencyModel(myBookCurrency = this.myBookCurrency ?:"")
}

fun GetBooksInfoCurrencyEntity.toGetBooksInfoCurrencyModel() : GetBooksInfoCurrencyModel {
    return GetBooksInfoCurrencyModel(myBookCurrency = this.myBookCurrency ?: "")
}

fun GetBooksBudgetEntity.toUiBookBudgetModel(): UiBookBudgetModel {
    val budgetList = listOf(
        BudgetItem("1", "${NumberFormat.getNumberInstance().format(this.january.toInt())}원", this.january > 0.0),
        BudgetItem("2","${NumberFormat.getNumberInstance().format(this.february.toInt())}원", this.february > 0.0),
        BudgetItem("3","${NumberFormat.getNumberInstance().format(this.march.toInt())}원", this.march > 0.0),
        BudgetItem("4", "${NumberFormat.getNumberInstance().format(this.april.toInt())}원", this.april > 0.0),
        BudgetItem("5", "${NumberFormat.getNumberInstance().format(this.may.toInt())}원", this.may > 0.0),
        BudgetItem("6", "${NumberFormat.getNumberInstance().format(this.june.toInt())}원", this.june > 0.0),
        BudgetItem("7", "${NumberFormat.getNumberInstance().format(this.july.toInt())}원", this.july > 0.0),
        BudgetItem("8", "${NumberFormat.getNumberInstance().format(this.august.toInt())}원", this.august > 0.0),
        BudgetItem("9","${NumberFormat.getNumberInstance().format(this.september.toInt())}원", this.september > 0.0),
        BudgetItem("10", "${NumberFormat.getNumberInstance().format(this.october.toInt())}원", this.october > 0.0),
        BudgetItem("11", "${NumberFormat.getNumberInstance().format(this.november.toInt())}원", this.november > 0.0),
        BudgetItem("12", "${NumberFormat.getNumberInstance().format(this.december.toInt())}원", this.december > 0.0)
    )
    return UiBookBudgetModel(
        budgetList = budgetList
    )
}
fun PostBooksCategoryAddEntity.toPostBooksCategoryAddModel() : PostBooksCategoryAddModel {
    return PostBooksCategoryAddModel(name = this.name ?: "")
}
fun GetBooksCodeEntity.toGetBooksCodeModel(): GetBooksCodeModel {
    return GetBooksCodeModel(this.code ?: "")
}
fun List<GetBookRepeatEntity>.toUiBookRepeatModel(): List<UiBookRepeatModel> {
    return this.map {
        val repeatDurationInKorean = when (it.repeatDuration) {
            "NONE" -> ""
            "EVERYDAY" -> "매일"
            "WEEK" -> "매주"
            "MONTH" -> "매달"
            "WEEKDAY" -> "주중"
            "WEEKEND" -> "주말"
            else -> it.repeatDuration // 만약 매칭되는 값이 없을 경우 기본값 사용
        }
        UiBookRepeatModel(
            it.id, it.description, repeatDurationInKorean, it.lineSubCategory, it.assetSubCategory, it.money.toInt(), false
        )
    }
}