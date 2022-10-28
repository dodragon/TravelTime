package com.dod.travel.view.user.join

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dod.data.model.MessageModel
import com.dod.data.model.UserModel
import com.dod.data.repository.user.JoinRepository
import kotlinx.coroutines.launch

class JoinViewModel(
    private val repo: JoinRepository
) : ViewModel(){

    val messageData: MutableLiveData<MessageModel> = MutableLiveData()

    fun userJoin(userModel: com.dod.data.model.UserModel) {
        viewModelScope.launch {
            messageData.value = repo.userJoin(userModel)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class JoinFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return JoinViewModel(JoinRepository.getInstance()!!) as T
        }
    }
}