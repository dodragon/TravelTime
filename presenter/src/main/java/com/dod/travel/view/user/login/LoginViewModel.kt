package com.dod.travel.view.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dod.data.model.MessageModel
import com.dod.data.model.UserModel
import com.dod.data.repository.group.GroupRepository
import com.dod.data.repository.user.LoginRepository
import kotlinx.coroutines.launch

/*===========================================
* @className    LoginViewModel.kt
* @author       dohyeon.kim
* @since        2022-10-21 오후 3:38
* @description  Login ViewModel
============================================ */
class LoginViewModel(
    private val repo: LoginRepository,
    private val groupRepo: GroupRepository
) : ViewModel() {

    val userData: MutableLiveData<UserModel> = MutableLiveData()
    val messageData: MutableLiveData<MessageModel> = MutableLiveData()

    fun login(email: String, pw: String, uid: String) {
        viewModelScope.launch {
            userData.value = repo.userLogin(email, pw, uid)
        }
    }

    fun snsLogin(email: String, uid: String, name: String) {
        viewModelScope.launch {
            userData.value = repo.userSnsLogin(email, uid, name)
        }
    }

    fun groupJoin(groupId: Long, userId: Long) {
        viewModelScope.launch {
            messageData.value = groupRepo.groupJoin(groupId, userId)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class LoginFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(LoginRepository.getInstance()!!, GroupRepository.getInstance()!!) as T
        }
    }
}