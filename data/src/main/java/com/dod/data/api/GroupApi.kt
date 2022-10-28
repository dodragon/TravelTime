package com.dod.data.api

import com.dod.data.model.GroupModel
import com.dod.data.model.MessageModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GroupApi {

    @FormUrlEncoded
    @POST("group/insert")
    suspend fun makeGroup(
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("userIdx") idx: Long
    ): Response<MessageModel>

    @GET("group/list")
    suspend fun getMainGroups(
        @Query("userIdx") userIdx: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<List<GroupModel>>

    @GET("group/userCnt")
    suspend fun getGroupUserCnt(
        @Query("groupId") groupId: Long
    ): Response<Int>

    @FormUrlEncoded
    @POST("group/join")
    suspend fun groupJoin(
        @Field("groupId") groupId: Long,
        @Field("userId") userId: Long
    ): Response<MessageModel>
}