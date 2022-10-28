package com.dod.travel.view.group

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dod.data.model.GroupModel
import com.dod.data.repository.group.GroupRepository

class GroupViewModel constructor(
    private val repo: GroupRepository
) : ViewModel() {

    fun getGroupList(userIdx: Long): LiveData<PagingData<GroupModel>>  =
        repo.getGroupPagingData(userIdx).cachedIn(viewModelScope)

    @Suppress("UNCHECKED_CAST")
    class GroupFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GroupViewModel(GroupRepository.getInstance()!!) as T
        }
    }
}