package com.dod.data.model

data class UserModel(
    override val idx: Long,
    val email: String,
    val password: String,
    val uid: String,
    var name: String,
    val teams: List<Long>,
    val profile: String,
    var message: MessageModel,
    val joinedAt: String
) : DefaultData, java.io.Serializable