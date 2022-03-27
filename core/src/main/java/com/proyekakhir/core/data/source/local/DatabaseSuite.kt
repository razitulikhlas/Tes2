package com.proyekakhir.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class,RemoteKey::class], version = 1,exportSchema = false)
abstract class DatabaseSuite : RoomDatabase() {
    abstract val suiteDao: SuiteDao
}