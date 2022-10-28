package com.dod.travel.view.share

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dod.data.model.MessageModel
import com.dod.data.repository.group.GroupRepository
import kotlinx.coroutines.launch

class KakaoShareViewModel(
    private val groupRepo: GroupRepository
) : ViewModel() {

    val userCnt = MutableLiveData<Int>()
    val messageData = MutableLiveData<MessageModel>()

    fun getUserCnt(groupId: Long) {
        viewModelScope.launch {
            userCnt.value = groupRepo.getGroupUserCnt(groupId)
        }
    }

    fun groupJoin(groupId: Long, userId: Long) {
        viewModelScope.launch {
            messageData.value = groupRepo.groupJoin(groupId, userId)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class KakaoShareFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return KakaoShareViewModel(GroupRepository.getInstance()!!) as T
        }
    }
}