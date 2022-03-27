package com.proyekakhir.core.domain.usecase

import androidx.paging.PagingData
import com.proyekakhir.core.data.source.Resource
import com.proyekakhir.core.data.source.local.UserEntity
import com.proyekakhir.core.domain.model.Events
import com.proyekakhir.core.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class EventIteractor(private val repository: EventRepository) : UseCaseEvent {
    override fun getEvents(): Flow<List<Events>> = repository.getEvents()
    override fun getUsers(): Flow<Resource<List<UserEntity>>> = repository.getUsers()
    override fun getUserPage(): Flow<PagingData<UserEntity>> = repository.getUserPage()
}