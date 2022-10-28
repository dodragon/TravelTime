package com.dod.data.repository.group

import com.dod.data.RetrofitInstance

class GroupMakeRepository {

    suspend fun makeGroup(name: String, description: String, idx: Long): com.dod.data.model.MessageModel {
        val response = RetrofitInstance.groupApi.makeGroup(name, description, idx)
        return if(response.isSuccessful) {
            response.body() as com.dod.data.model.MessageModel
        }else {
            com.dod.data.model.MessageModel(true, "그룹 만들기에 실패했습니다.")
        }
    }

    companion object {
        private var instance: GroupMakeRepository? = null

        fun getInstance(): GroupMakeRepository? {
            if(instance == null) {
                instance = GroupMakeRepository()
            }
            return instance
        }
    }
}