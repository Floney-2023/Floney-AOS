package com.aos.repository

import com.aos.model.alarm.UiAlarmGetModel
import com.aos.model.analyze.UiAnalyzeAssetModel
import com.aos.model.analyze.UiAnalyzeCategoryInComeModel
import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel
import com.aos.model.analyze.UiAnalyzePlanModel

interface AlarmRepository {

    // 알람 정보 조회
    suspend fun getAlarm(bookKey: String): Result<List<UiAlarmGetModel>>

}