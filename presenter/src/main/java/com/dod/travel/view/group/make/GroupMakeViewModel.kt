package com.dod.travel.view.group.make

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dod.data.model.MessageModel
import com.dod.data.repository.group.GroupMakeRepository
import kotlinx.coroutines.launch

class GroupMakeViewModel(
    private val repo: GroupMakeRepository
) : ViewModel() {

    val messageData: MutableLiveData<MessageModel> = MutableLiveData()

    fun makeGroup(name: String, description: String, userIdx: Long) {
        viewModelScope.launch {
            messageData.value = repo.makeGroup(name, description, userIdx)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class GroupMakeFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GroupMakeViewModel(GroupMakeRepository.getInstance()!!) as T
        }
    }
}