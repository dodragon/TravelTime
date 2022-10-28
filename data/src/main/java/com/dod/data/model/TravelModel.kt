package com.dod.data.model

data class TravelModel(
    val travelId: Long,
    val name: String,
    val description: String,
    val budget: Int,
    val group: GroupModel,
    val joinUserEa: Int,
    val startDate: String,
    val endDate: String,
    val isEnd: Boolean,
    val noticeList: List<TravelNoticeModel>,
    val carList: List<CarModel>,
    val eatListL: List<EatModel>,
    val playList: List<PlayModel>,
    val stayList: List<StayModel>,
    val createdAt: String
) : java.io.Serializable
