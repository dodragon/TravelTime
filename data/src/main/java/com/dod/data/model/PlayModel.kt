package com.dod.data.model

import java.time.LocalDateTime

data class PlayModel(
    val playId: Long,
    val title: String,
    val description: String,
    val price: Int,
    val link: String,
    val image: String,
    val playTime: LocalDateTime,
    val travelId: Long,
    val createUser: UserModel
)
