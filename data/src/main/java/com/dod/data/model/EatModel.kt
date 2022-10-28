package com.dod.data.model

data class EatModel(
    val eatId: Long,
    val name: String,
    val description: String,
    val price: Int,
    val link: String,
    val image: String,
    val travelId: Long,
    val createUser: UserModel
) : java.io.Serializable