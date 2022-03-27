package com.proyekakhir.core.data.source

import com.proyekakhir.core.data.source.remote.network.ApiResponse
import com.proyekakhir.core.data.source.remote.network.ApiService
import com.proyekakhir.core.data.source.remote.response.DataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getUser(page:Int): Flow<ApiResponse<List<DataItem>>> {
        return flow {
            try {
                val response = apiService.getListUser(page)
                val dataArray = response.data
                if (dataArray!!.isNotEmpty()) {
                    emit(ApiResponse.Success(response.data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserPage(page: Int) = apiService.getListUser(page)
}