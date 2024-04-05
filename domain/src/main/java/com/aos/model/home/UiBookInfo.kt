package com.aos.model.home

import androidx.recyclerview.widget.DiffUtil
import timber.log.Timber

data class UiBookInfoModel(
    val bookImg: String,
    val bookName: String,
    val startDay: String,
    val seeProfileStatus: Boolean,
    val carryOver: String,
    val ourBookUsers: List<OurBookUsers>
)

data class OurBookUsers(
    val name: String,
    val profileImg: String,
    val role: String,
    val me: Boolean
)
