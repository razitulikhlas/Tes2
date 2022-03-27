package com.proyekakhir.core.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.proyekakhir.core.data.source.local.RemoteKey
import com.proyekakhir.core.data.source.local.UserEntity
import com.proyekakhir.core.utils.DataMapper
import retrofit2.HttpException
import java.io.IOException


const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class RemoteMediators(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) :
    RemoteMediator<Int, UserEntity>() {


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = remoteDataSource.getUserPage(page)
            val isEndOfList = response.data?.isEmpty()

            if (loadType == LoadType.REFRESH) {
                localDataSource.deleteRemoteKey()
                localDataSource.deleteUser()
            }
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (isEndOfList!!) null else page + 1
            val keys = response.data.map {
                RemoteKey(it.id!!, prevKey = prevKey, nextKey = nextKey)
            }
            localDataSource.insertRemoteKey(keys)
            localDataSource.insertUser(DataMapper.mapResponseToUserEntity(response.data))
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UserEntity>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                localDataSource.remoteKeysSuitId(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, UserEntity>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { user -> localDataSource.remoteKeysSuitId(user.id!!) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, UserEntity>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { user -> localDataSource.remoteKeysSuitId(user.id!!) }
    }
}