package com.aos.data.repository.remote.alarm

import com.aos.data.entity.request.analyze.PostAnalyzeAssetBody
import com.aos.data.entity.request.analyze.PostAnalyzeBudgetBody
import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.response.alarm.GetAlarmEntity
import com.aos.data.entity.response.analyze.PostAnalyzeAssetEntity
import com.aos.data.entity.response.analyze.PostAnalyzeBudgetEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryInComeEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryOutComeEntity
import com.aos.util.NetworkState

interface AlarmRemoteDataSource {

    suspend fun getAlarm(bookKey: String): NetworkState<List<GetAlarmEntity>>
}