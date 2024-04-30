package com.aos.data.api

import com.aos.data.entity.response.alarm.GetAlarmEntity
import com.aos.data.entity.response.book.GetBookRepeatEntity
import com.aos.util.NetworkState
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AlarmService {
    @GET("alarm")
    @Headers("Auth: true")
    suspend fun getAlarm(
        @Query("bookKey") bookKey: String
    ): NetworkState<List<GetAlarmEntity>>
}