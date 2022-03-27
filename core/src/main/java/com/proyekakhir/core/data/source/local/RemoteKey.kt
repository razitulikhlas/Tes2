package com.proyekakhir.core.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val suitId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)