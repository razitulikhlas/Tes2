package com.proyekakhir.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Events(
    var id: Int,
    var title: String,
    var name:String,
    var description: String,
    var date:String,
    var imageProfile: String,
    var ImageEvent: String,
    var latitude: Double,
    var longitude: Double,
) : Parcelable