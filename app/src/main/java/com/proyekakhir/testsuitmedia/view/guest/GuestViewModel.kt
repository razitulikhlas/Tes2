package com.proyekakhir.testsuitmedia.view.guest

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.proyekakhir.core.data.source.Resource
import com.proyekakhir.core.data.source.local.UserEntity
import com.proyekakhir.core.domain.usecase.UseCaseEvent

class GuestViewModel(private val useCaseEvent: UseCaseEvent) : ViewModel() {

    val users = MediatorLiveData<Resource<List<UserEntity>>>()

    fun getUsers() {
        users.addSource(useCaseEvent.getUsers().asLiveData()) {
            users.value = it
        }
    }

    val guest = MediatorLiveData<PagingData<UserEntity>>()

    fun getGuest(){
        guest.addSource(useCaseEvent.getUserPage().asLiveData().cachedIn(viewModelScope)){
            guest.value = it
        }
    }
}