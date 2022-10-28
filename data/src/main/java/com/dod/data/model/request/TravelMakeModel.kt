package com.dod.data.model.request

import com.dod.data.model.UserModel

data class TravelMakeModel(
    val name: String,
    val description: String,
    val groupId: Long,
    val budget: Int,
    val userList: List<UserModel>,
    val startDate: String,
    val endDate: String
): java.io.Serializable
