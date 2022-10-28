package com.dod.travel.util.sns

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmailLogin(
    private val context: Context,
    private val listener: LoginListener
) {

    private val auth: FirebaseAuth = Firebase.auth

    fun login(email: String, pw: String) {
        auth.signInWithEmailAndPassword(email, pw)
            .addOnSuccessListener() {
                val user = auth.currentUser
                listener.checkUser(email, pw, user!!.uid, "", false)
            }
    }
}