package com.proyekakhir.testsuitmedia.di

import com.proyekakhir.core.adapter.EventAdapter
import com.proyekakhir.core.adapter.EventMapsAdapter
import com.proyekakhir.core.adapter.GuestAdapter
import com.proyekakhir.testsuitmedia.view.HomeViewModel
import com.proyekakhir.testsuitmedia.view.event.EventViewModel
import com.proyekakhir.testsuitmedia.view.guest.GuestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { HomeViewModel() }
    viewModel {
        EventViewModel(get())
    }
    single { EventAdapter() }
    single { EventMapsAdapter() }
    viewModel{ GuestViewModel(get()) }
    single { GuestAdapter() }
}

