package com.dod.travel.util.sns

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.dod.travel.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleLogin(
    private val context: Context,
    private val launcher: ActivityResultLauncher<Intent>,
    private val listener: LoginListener
) {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var signInClient: GoogleSignInClient

    fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        signInClient = GoogleSignIn.getClient(context, gso)
        launcher.launch(signInClient.signInIntent)
    }

    fun requestAuth(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                val user = it.user
                if(user != null) {
                    listener.checkUser(user.email!!, "", user.uid, user.displayName!!, true)
                }else {
                    Toast.makeText(context, context.getString(R.string.no_user), Toast.LENGTH_SHORT).show()
                    //TODO: Go Join
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
    }
}