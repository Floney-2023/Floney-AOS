package com.aos.data.repository.remote.alarm

import com.aos.data.api.AlarmService
import com.aos.data.api.AnalyzeService
import com.aos.data.entity.request.alarm.PostAlarmSaveBody
import com.aos.data.entity.request.alarm.PostAlarmUpdateBody
import com.aos.data.entity.request.analyze.PostAnalyzeAssetBody
import com.aos.data.entity.request.analyze.PostAnalyzeBudgetBody
import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.response.alarm.GetAlarmEntity
import com.aos.data.entity.response.analyze.PostAnalyzeAssetEntity
import com.aos.data.entity.response.analyze.PostAnalyzeBudgetEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryInComeEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryOutComeEntity
import com.aos.util.NetworkState
import javax.inject.Inject

class AlarmRemoteDataSourceImpl @Inject constructor(private val alarmService: AlarmService) :
    AlarmRemoteDataSource {
    override suspend fun getAlarm(
        bookKey : String
    ): NetworkState<List<GetAlarmEntity>> {
        return alarmService.getAlarm(bookKey)
    }

    override suspend fun postAlarmUpdate(
        bookKey: String,
        postAlarmUpdateBody: PostAlarmUpdateBody
    ): NetworkState<Void> {
        return alarmService.postAlarmUpdate(bookKey, postAlarmUpdateBody)
    }

    override suspend fun postAlarmSave(
        postAlarmSaveBody: PostAlarmSaveBody
    ): NetworkState<Void> {
        return alarmService.postAlarmSave(postAlarmSaveBody)
    }
}