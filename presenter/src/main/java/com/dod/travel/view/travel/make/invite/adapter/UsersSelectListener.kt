package com.dod.travel.view.travel.make.invite.adapter

import com.dod.data.model.UserModel

interface UsersSelectListener {
    fun addUser(user: UserModel)
    fun deleteUser(user: UserModel)
}