package com.aos.floney.ext

import android.provider.Settings.Global.getString
import com.aos.floney.R
import org.json.JSONObject
import timber.log.Timber

fun String?.parseErrorMsg(): String {
    return if(this == "") {
        ""
    } else if(this == "NetworkError") {
        "네트워크에 연결되어 있지 않거나 원활하지 않습니다."
    } else if(this == "unKnownError") {
        "예상 못한 에러 발생! 플로니팀에 제보 해주세요."
    } else {
        val jsonObject = JSONObject(this)
        jsonObject.getString("message")
    }
}