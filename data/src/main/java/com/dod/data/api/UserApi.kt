package com.dod.data.api

import com.dod.data.model.MessageModel
import com.dod.data.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("uid") uid: String
    ): Response<UserModel>

    @FormUrlEncoded
    @POST("user/snslogin")
    suspend fun userSnsLogin(
        @Field("email") email: String,
        @Field("uid") uid: String,
        @Field("name") name: String
    ): Response<UserModel>

    @POST("user/join")
    suspend fun userJoin(
        @Body user: UserModel
    ): Response<MessageModel>

    @GET("user/groupList")
    suspend fun groupUserList(
        @Query("idx") ids: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<UserModel>>

    @PUT("user/update/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: Long,
        @Body user: UserModel
    ): Response<UserModel>
}