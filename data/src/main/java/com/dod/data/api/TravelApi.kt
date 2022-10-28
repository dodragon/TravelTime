package com.dod.data.api

import com.dod.data.model.MessageModel
import com.dod.data.model.TravelModel
import com.dod.data.model.request.TravelMakeModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TravelApi {

    @POST("travel/insert")
    suspend fun travelInsert(
        @Body travel: TravelMakeModel
    ): Response<MessageModel>

    @GET("travel/list")
    suspend fun getTravelList(
        @Query("groupId") groupId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<TravelModel>>
}