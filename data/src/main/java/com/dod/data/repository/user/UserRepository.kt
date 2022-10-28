package com.dod.data.repository.user

import com.dod.data.RetrofitInstance
import com.dod.data.model.MessageModel
import com.dod.data.model.UserModel

class UserRepository {

    suspend fun userUpdate(userId: Long, user: UserModel): UserModel {
        val response = RetrofitInstance.userApi.updateUser(userId, user)
        return if(response.isSuccessful) {
            response.body() as UserModel
        }else {
            user.message = MessageModel(true, "통신에 실패했습니다.")
            user
        }
    }

    companion object {
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository? {
            if(instance == null) {
                instance = UserRepository()
            }
            return instance
        }
    }
}