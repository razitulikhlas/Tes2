package com.proyekakhir.core.data.source.remote.network

import com.proyekakhir.core.data.source.remote.response.ResponseUser
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getListUser(
        @Query("page") page: Int
    ): ResponseUser
}