package com.aos.usecase.mypage

import com.aos.model.alarm.UiAlarmGetModel
import com.aos.repository.AlarmRepository
import com.aos.repository.UserRepository
import javax.inject.Inject

class AlarmSaveGetUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
){
    suspend operator fun invoke(
        bookKey : String,
        title : String,
        body : String,
        imgUrl : String,
        userEmail : String,
        date : String
    ): Result<Void?> {
        return alarmRepository.postAlarmSave(bookKey, title, body, imgUrl, userEmail, date)
    }

}