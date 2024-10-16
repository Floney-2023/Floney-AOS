package com.aos.repository

import com.aos.model.alarm.UiAlarmGetModel
import com.aos.model.analyze.UiAnalyzeAssetModel
import com.aos.model.analyze.UiAnalyzeCategoryInComeModel
import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel
import com.aos.model.analyze.UiAnalyzePlanModel

interface AlarmRepository {

    // 알람 정보 조회
    suspend fun getAlarm(bookKey: String): Result<List<UiAlarmGetModel>>

    // 알람 읽음 처리
    suspend fun postAlarmUpdate(bookKey: String, id: Int): Result<Void?>

    // 알람 정보 저장
    suspend fun postAlarmSave(bookKey: String, title:String, body:String, imgUrl:String, userEmail: String, date:String): Result<Void?>
}