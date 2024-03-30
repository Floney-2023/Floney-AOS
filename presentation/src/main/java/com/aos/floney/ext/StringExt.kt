package com.aos.floney.ext

import org.json.JSONObject
import timber.log.Timber

fun String?.parseErrorMsg(): String {
    return if(this == "") {
        ""
    } else {
        val jsonObject = JSONObject(this)
        jsonObject.getString("message")
    }
}