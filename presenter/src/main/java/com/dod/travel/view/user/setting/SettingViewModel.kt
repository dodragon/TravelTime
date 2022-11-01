package com.dod.travel.view.user.setting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dod.data.model.UserModel
import com.dod.data.repository.image.ImageRepository
import com.dod.data.repository.user.UserRepository
import kotlinx.coroutines.launch
import java.io.File

class SettingViewModel(
    private val repo: UserRepository,
    private val imageRepo: ImageRepository
) : ViewModel() {

    val userData = MutableLiveData<UserModel>()

    fun updateUser(user: UserModel) {
        viewModelScope.launch {
            userData.value = repo.userUpdate(user.idx, user)
        }
    }

    fun upLoadImage(byteArray: ByteArray, folder: String, name: String, listener: ImageUploadListener) {
        imageRepo.saveImage(byteArray, folder, name)
            .addOnSuccessListener {
                Log.e("upload Uri", it.uploadSessionUri.toString())
                Log.e("upload meta", it.metadata.toString())
                listener.success()
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    interface ImageUploadListener {
        fun success()
    }

    @Suppress("UNCHECKED_CAST")
    class SettingFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingViewModel(UserRepository.getInstance()!!, ImageRepository.getInstance()!!) as T
        }
    }
}