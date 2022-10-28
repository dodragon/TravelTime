package com.dod.data.model

data class OilModel(
    val oilId: Long,
    val carId: Long,
    val oilPrice: Int,
    val moveKm: Int,
    val createdAt: String
): java.io.Serializable
