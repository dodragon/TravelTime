package com.dod.data.repository.user

import com.dod.data.RetrofitInstance
import com.dod.data.model.UserModel

/*===========================================
* @className    LoginRepository.kt
* @author       dohyeon.kim
* @since        2022-10-21 오후 3:38
* @description  Login Repository for Retrofit
============================================ */
class LoginRepository {

    suspend fun userLogin(email: String, pw: String, uid: String): UserModel? {
        val response = RetrofitInstance.userApi.userLogin(email, pw, uid)
        return if(response.isSuccessful) {
            response.body() as UserModel
        }else {
            null
        }
    }

    suspend fun userSnsLogin(email: String, uid: String, name: String): UserModel? {
        val response = RetrofitInstance.userApi.userSnsLogin(email, uid, name)
        return if(response.isSuccessful) {
            response.body() as UserModel
        }else {
            null
        }
    }

    companion object {
        private var instance: LoginRepository? = null

        fun getInstance(): LoginRepository? {
            if(instance == null) {
                instance = LoginRepository()
            }
            return instance
        }
    }
}