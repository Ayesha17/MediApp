package com.ayesha.mediapp.data.remote

import com.ayesha.core.domain.CloudResult
import com.ayesha.core.domain.Failure
import com.ayesha.mediapp.data.database.entities.User

interface UserDataSource {
    suspend fun getCurrentUser(): CloudResult<Failure, User>

    suspend fun saveUser(user: User): CloudResult<Failure, Long>

}