package com.aos.data.mapper

import android.graphics.Color
import com.aos.data.entity.response.analyze.PostAnalyzeBudgetEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryInComeEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryOutComeEntity
import com.aos.model.analyze.AnalyzeResult
import com.aos.model.analyze.UiAnalyzeCategoryInComeModel
import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel
import com.aos.model.analyze.UiAnalyzePlanModel
import timber.log.Timber
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random

private val colorUsedArr = arrayListOf<Int>()
private val colorArr = listOf<Int>(
    Color.parseColor("#AD1F25"),
    Color.parseColor("#EFA9AB"),
    Color.parseColor("#FF5C00"),
    Color.parseColor("#FFBE99"),
    Color.parseColor("#E4BF00"),
    Color.parseColor("#FFEE97"),
    Color.parseColor("#0060E5"),
    Color.parseColor("#4C97FF"),
    Color.parseColor("#99C4FF"),
    Color.parseColor("#35347F"),
    Color.parseColor("#4A48B0"),
    Color.parseColor("#706EC4"),
    Color.parseColor("#654CFF"),
    Color.parseColor("#9B8BFF"),
    Color.parseColor("#D3CCFF")
)
private val randomColorArr = arrayListOf<Int>()
private var stepIdx = 0
private var colorIdx = 0

fun PostAnalyzeCategoryOutComeEntity.toUiAnalyzeModel(): UiAnalyzeCategoryOutComeModel {
    colorIdx = 0
    stepIdx = 0
    colorUsedArr.clear()
    randomColorArr.clear()
    getRandomColor(this.analyzeResult.size)

    return UiAnalyzeCategoryOutComeModel(total = "총 ${
        NumberFormat.getNumberInstance().format(this.total)
    }원을\n소비했어요",
        differance = "${
            if (this.differance > this.total) {
                "저번달 대비 ${
                    NumberFormat.getNumberInstance().format(this.differance - total)
                }원을\n덜 사용했어요"
            } else {
                "저번달 대비 ${
                    NumberFormat.getNumberInstance().format(this.total - differance)
                }원을\n더 사용했어요"
            }
        }",
        size = this.analyzeResult.size,
        analyzeResult = this.analyzeResult.map {
            AnalyzeResult(
                category = it.category,
                money = it.money,
                uiMoney = "${NumberFormat.getNumberInstance().format(it.money)}원",
                percent = (it.money / total) * 100.0,
                uiPercent = "${((it.money / total) * 100.0).round(1)}%",
                color = when (stepIdx) {
                    0 -> {
                        stepIdx++
                        Color.parseColor("#FFDE31")
                    }

                    1 -> {
                        stepIdx++
                        Color.parseColor("#FF965B")
                    }

                    2 -> {
                        stepIdx++
                        Color.parseColor("#E56E73")
                    }

                    else -> {
                        randomColorArr[colorIdx++]
                    }
                }
            )
        })
}

fun PostAnalyzeCategoryInComeEntity.toUiAnalyzeModel(): UiAnalyzeCategoryInComeModel {
    colorIdx = 0
    stepIdx = 0
    colorUsedArr.clear()
    randomColorArr.clear()
    getRandomColor(this.analyzeResult.size)

    return UiAnalyzeCategoryInComeModel(total = "총 ${
        NumberFormat.getNumberInstance().format(this.total)
    }원을\n소비했어요",
        differance = "${
            if (this.differance > this.total) {
                "저번달 대비 ${
                    NumberFormat.getNumberInstance().format(this.differance - total)
                }원을\n덜 사용했어요"
            } else {
                "저번달 대비 ${
                    NumberFormat.getNumberInstance().format(this.total - differance)
                }원을\n더 사용했어요"
            }
        }",
        size = this.analyzeResult.size,
        analyzeResult = this.analyzeResult.map {
            AnalyzeResult(
                category = it.category,
                money = it.money,
                uiMoney = "${NumberFormat.getNumberInstance().format(it.money)}원",
                percent = (it.money / total) * 100.0,
                uiPercent = "${((it.money / total) * 100.0).round(1)}%",
                color = when (stepIdx) {
                    0 -> {
                        stepIdx++
                        Color.parseColor("#4C97FF")
                    }

                    1 -> {
                        stepIdx++
                        Color.parseColor("#35347F")
                    }

                    2 -> {
                        stepIdx++
                        Color.parseColor("#9B8BFF")
                    }

                    else -> {
                        randomColorArr[colorIdx++]
                    }
                }
            )
        })
}

fun PostAnalyzeBudgetEntity.toUiAnalyzePlanModel(): UiAnalyzePlanModel {
    val tempLeftMoney = "${NumberFormat.getNumberInstance().format(this.leftMoney)}원".substring(
        1, "${NumberFormat.getNumberInstance().format(this.leftMoney)}원".length
    )

    // 남은 일 수 계산 
    val calendar = Calendar.getInstance()
    val today = calendar.get(Calendar.DAY_OF_MONTH)
    val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val enemyDay = lastDayOfMonth - today + 1

    return UiAnalyzePlanModel(
        initBudget = "${NumberFormat.getNumberInstance().format(this.initBudget)}원",
        leftMoney = "${NumberFormat.getNumberInstance().format(this.leftMoney)}원",
        percent = if (this.leftMoney < 0) {
            "100"
        } else {
            ((this.leftMoney / this.initBudget) * 100).toInt().toString()
        },
        divMoney = "${
            NumberFormat.getNumberInstance().format((this.leftMoney / enemyDay).round(2))
        }원".replace("-", "")
    )
}

// 랜덤 색상 가져오기 그래프는 3개까지는 색상 고정 그 이후는 랜덤 색상임
fun getRandomColor(repeat: Int): List<Int> {
    if (repeat > 0) {
        var random = 0
        for (i in 0..repeat) {
            random = Random.nextInt(0, 14)
            if (!colorUsedArr.contains(random)) {
                colorUsedArr.add(random)
                randomColorArr.add(colorArr[random])
            }
        }
    }
    return randomColorArr
}

fun Double.round(decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    Timber.e("factor $factor")
    Timber.e("double $this")
    return (this * factor).roundToInt() / factor
}