package com.aos.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.aos.data.entity.response.alarm.GetAlarmEntity
import com.aos.data.entity.response.book.GetBookRepeatEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.model.alarm.UiAlarmGetModel
import com.aos.model.book.UiBookRepeatModel
import com.aos.model.home.GetCheckUserBookModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
fun List<GetAlarmEntity>.toUiAlarmGetEntity(): List<UiAlarmGetModel> {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    return this.map { alarm ->
        val alarmTime = LocalDateTime.parse(alarm.date, formatter) // String to LocalDateTime
        val minutesDiff = ChronoUnit.MINUTES.between(alarmTime, now)
        val timeAgo = when {
            minutesDiff < 60 -> "${minutesDiff}분 전"
            minutesDiff < 1440 -> "${minutesDiff / 60}시간 전"
            minutesDiff < 10080 -> "${minutesDiff / 1440}일 전"
            minutesDiff < 43200 -> "${minutesDiff / 10080}주 전"
            else -> "${minutesDiff / 43200}개월 전"
        }

        UiAlarmGetModel(
            id = alarm.id,
            title = alarm.title,
            body = alarm.body,
            imgUrl = alarm.imgUrl,
            date = timeAgo, // 이제 'date'는 경과한 시간으로 표시됩니다.
            received = alarm.received
        )
    }
}