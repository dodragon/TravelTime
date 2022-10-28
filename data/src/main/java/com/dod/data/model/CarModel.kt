package com.dod.data.model

enum class CarType{
    MINE,
    RENT,
    TRANS,
    NONE
}

data class CarModel(
    val carOptionId: Long,
    val name: String,
    val description: String,
    val link: String,
    val image: String,
    val type: CarType,
    val sitEa: Int,
    val rentPrice: Int,
    val transPrice: Int,
    val oil: List<OilModel>,
    val travelId: Long,
    val createUser: UserModel
) : java.io.Serializable