package com.dod.travel.view.user.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dod.data.model.UserModel
import com.dod.data.repository.user.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel(
    private val repo: UserRepository
) : ViewModel() {

    val userData = MutableLiveData<UserModel>()

    fun updateUser(user: UserModel) {
        viewModelScope.launch {
            userData.value = repo.userUpdate(user.idx, user)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class SettingFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingViewModel(UserRepository.getInstance()!!) as T
        }
    }
}