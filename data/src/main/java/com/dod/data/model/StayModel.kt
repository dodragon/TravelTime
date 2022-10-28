package com.dod.data.model

data class StayModel(
    val stayId: Long,
    val name: String,
    val description: String,
    val price: Int,
    val addPersonPrice: Int,
    val subPrice: Int,
    val link: String,
    val image: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val travelId: Long,
    val createUser: UserModel
) : java.io.Serializable
