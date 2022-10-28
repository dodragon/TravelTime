package com.dod.data.model

data class TravelNoticeModel(
    val tnId: Long,
    val title: String,
    val content: String,
    val createUser: UserModel,
    val createdAt: String,
    val travelId: Long
) : java.io.Serializable
