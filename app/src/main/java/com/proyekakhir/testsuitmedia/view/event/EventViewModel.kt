package com.proyekakhir.testsuitmedia.view.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.proyekakhir.core.domain.usecase.UseCaseEvent

class EventViewModel(useCase: UseCaseEvent) : ViewModel() {
    val event = useCase.getEvents().asLiveData()
}