package com.dod.travel.util.sns

interface LoginListener {
    fun checkUser(email: String, pw: String, uid: String, name: String, isSns: Boolean)
}