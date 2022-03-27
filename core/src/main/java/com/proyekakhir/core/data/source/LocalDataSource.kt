package com.proyekakhir.core.data.source

import androidx.paging.PagingSource
import com.proyekakhir.core.data.source.local.RemoteKey
import com.proyekakhir.core.data.source.local.SuiteDao
import com.proyekakhir.core.data.source.local.UserEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val suiteDao: SuiteDao) {
    fun getListUser(): Flow<List<UserEntity>> = suiteDao.getListUser()

    fun getListUserPaging():PagingSource<Int,UserEntity> = suiteDao.getListUserPaging()

    suspend fun insertUser(list: List<UserEntity>) = suiteDao.insertAll(list)

    suspend fun deleteUser() = suiteDao.clearAll()

    suspend fun insertRemoteKey(list: List<RemoteKey>) = suiteDao.insertAllRemoteKey(list)

    suspend fun remoteKeysSuitId(id:Int) = suiteDao.remoteKeysSuitId(id)

    suspend fun deleteRemoteKey() = suiteDao.deleteAllRemoteKey()
}