package com.dod.data.model

enum class TravelJob{
    CAR,
    ROOM,
    EAT,
    PLAY,
    NONE,
    MAIN_EVENT,
    MONEY,
    LEADER
}

data class TravelUserModel(
    override val idx: Long,
    val user: UserModel,
    val job: List<TravelJob>
) : DefaultData