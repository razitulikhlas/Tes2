package com.proyekakhir.core.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SuiteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<UserEntity>)

    @Query("SELECT * FROM TBL_USER")
    fun getListUser(): Flow<List<UserEntity>>


    @Query("SELECT * FROM TBL_USER")
    fun getListUserPaging(): PagingSource<Int,UserEntity>

    @Query("DELETE FROM TBL_USER")
    suspend fun clearAll()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKey(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE suitId = :id")
    suspend fun remoteKeysSuitId(id: Int): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKey()
}