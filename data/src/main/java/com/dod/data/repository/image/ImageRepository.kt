package com.dod.data.repository.image

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.io.File

class ImageRepository {

    private val storage = Firebase.storage

    fun saveImage(byteArray: ByteArray, folder: String, name: String): UploadTask {
        val storageRef = storage.reference
        val imageRef = storageRef.child("$folder/$name")
        return imageRef.putBytes(byteArray)
    }

    companion object {
        private var instance: ImageRepository? = null

        fun getInstance(): ImageRepository? {
            if(instance == null) {
                instance = ImageRepository()
            }
            return instance
        }
    }
}