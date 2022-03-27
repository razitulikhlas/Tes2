package com.proyekakhir.core.utils

import com.proyekakhir.core.data.source.local.UserEntity
import com.proyekakhir.core.data.source.remote.response.DataItem

object DataMapper {
    fun mapResponseToUserEntity(response: List<DataItem>): List<UserEntity> {
        val listUser = ArrayList<UserEntity>()
        response.map {
            val user = UserEntity(
                id = it.id,
                fullName = "${it.firstName} ${it.lastName}" ,
                avatar = it.avatar,
                email = it.email,
            )
            listUser.add(user)
        }
        return listUser
    }
}