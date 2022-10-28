package com.dod.data.repository.user

import com.dod.data.RetrofitInstance

/*===========================================
* @className    JoinRepository.kt
* @author       dohyeon.kim
* @since        2022-10-21 오후 3:42
* @description  Join Repository
============================================ */
class JoinRepository {

    suspend fun userJoin(userModel: com.dod.data.model.UserModel): com.dod.data.model.MessageModel {
        val response = RetrofitInstance.userApi.userJoin(userModel)
        return if(response.isSuccessful) {
            response.body() as com.dod.data.model.MessageModel
        }else {
            com.dod.data.model.MessageModel(true, "오류가 발생했습니다.")
        }
    }

    companion object {
        private var instance: JoinRepository? = null

        fun getInstance(): JoinRepository? {
            if(instance == null) {
                instance = JoinRepository()
            }
            return instance
        }
    }
}