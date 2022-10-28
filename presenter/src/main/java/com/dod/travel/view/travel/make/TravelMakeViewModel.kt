package com.dod.travel.view.travel.make

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dod.data.model.MessageModel
import com.dod.data.model.request.TravelMakeModel
import com.dod.data.repository.travel.TravelRepository
import kotlinx.coroutines.launch

class TravelMakeViewModel(
    private val travelRepo: TravelRepository
) : ViewModel() {

    val messageData = MutableLiveData<MessageModel>()

    fun makeTravel(data: TravelMakeModel) {
        viewModelScope.launch {
            messageData.value = travelRepo.travelInsert(data)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class TravelMakeFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TravelMakeViewModel(TravelRepository.getInstance()!!) as T
        }
    }
}