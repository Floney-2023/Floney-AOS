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
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
fun List<GetAlarmEntity>.toUiAlarmGetEntity(): List<UiAlarmGetModel> {
    val now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")) // 현재 시간을 한국 시간대로 설정
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    return this.map { alarm ->
        val alarmTime = LocalDateTime.parse(alarm.date, formatter)
            .atZone(ZoneId.of("UTC")) // alarm.date가 UTC라고 가정
            .withZoneSameInstant(ZoneId.of("Asia/Seoul")) // 한국 시간대로 변환
        val minutesDiff = ChronoUnit.MINUTES.between(alarmTime, now)
        val timeAgo = when {
            minutesDiff <= 0 -> "방금"
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
