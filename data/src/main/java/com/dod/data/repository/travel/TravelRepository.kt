package com.dod.data.repository.travel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.dod.data.RetrofitInstance
import com.dod.data.model.MessageModel
import com.dod.data.model.TravelModel
import com.dod.data.model.request.TravelMakeModel
import com.dod.data.util.TravelPagingSource
import com.dod.data.util.UserPagingSource

class TravelRepository  {

    fun getGroupUsers(groupId: Long) =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(groupId, RetrofitInstance.userApi) }
        ).liveData

    suspend fun travelInsert(travel: TravelMakeModel): MessageModel {
        val response = RetrofitInstance.travelApi.travelInsert(travel)
        return if(response.isSuccessful) {
            response.body() as MessageModel
        }else {
            MessageModel(true, "통신에 실패했습니다.")
        }
    }

    fun getTravelList(groupId: Long) =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TravelPagingSource(groupId, RetrofitInstance.travelApi) }
        ).liveData

    companion object {
        private var instance: TravelRepository? = null

        fun getInstance(): TravelRepository? {
            if(instance == null) {
                instance = TravelRepository()
            }
            return instance
        }
    }
}