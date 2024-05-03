package com.aos.usecase.mypage

import com.aos.model.alarm.UiAlarmGetModel
import com.aos.repository.AlarmRepository
import com.aos.repository.UserRepository
import javax.inject.Inject

class AlarmInformGetUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
){
    suspend operator fun invoke(
        bookKey : String
    ): Result<List<UiAlarmGetModel>> {
        return alarmRepository.getAlarm(bookKey)
    }

}