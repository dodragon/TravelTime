package com.dod.data.repository.group

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.dod.data.RetrofitInstance
import com.dod.data.model.MessageModel
import com.dod.data.util.GroupPagingSource

class GroupRepository {

    fun getGroupPagingData(userIdx: Long) =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GroupPagingSource(userIdx, RetrofitInstance.groupApi) }
        ).liveData

    suspend fun getGroupUserCnt(groupId: Long): Int {
        val response = RetrofitInstance.groupApi.getGroupUserCnt(groupId)
        return if(response.isSuccessful) {
            response.body() as Int
        }else {
            0
        }
    }

    suspend fun groupJoin(groupId: Long, userId: Long): MessageModel {
        val response = RetrofitInstance.groupApi.groupJoin(groupId, userId)
        return if(response.isSuccessful) {
            response.body() as MessageModel
        }else {
            MessageModel(true, "통신에 실패했습니다.")
        }
    }

    companion object {
        private var instance: GroupRepository? = null

        fun getInstance(): GroupRepository? {
            if(instance == null){
                instance = GroupRepository()
            }
            return instance
        }
    }
}