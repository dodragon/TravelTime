package com.dod.data.repository.travel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.dod.data.RetrofitInstance
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