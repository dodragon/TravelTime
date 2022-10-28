package com.dod.travel.view.travel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dod.data.model.UserModel
import com.dod.data.repository.travel.TravelRepository

/*===========================================
* @className    TravelViewModel.kt
* @author       dohyeon.kim
* @since        2022-10-25 오전 9:48
* @description  Travle Activity ViewModel
============================================ */
class TravelViewModel(
    private val repo: TravelRepository
) : ViewModel() {

    fun getGroupUserList(groupId: Long): LiveData<PagingData<UserModel>> =
        repo.getGroupUsers(groupId).cachedIn(viewModelScope)

    @Suppress("UNCHECKED_CAST")
    class TravelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TravelViewModel(TravelRepository.getInstance()!!) as T
        }
    }
}