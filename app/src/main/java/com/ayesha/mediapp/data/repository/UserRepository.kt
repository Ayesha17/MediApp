package com.ayesha.mediapp.data.repository

import com.ayesha.core.domain.CloudResult
import com.ayesha.core.domain.Failure
import com.ayesha.mediapp.data.database.entities.User
import com.ayesha.mediapp.data.remote.LocalUserDataSource

class UserRepository(private val localUserDataSource: LocalUserDataSource) {

  suspend  fun addUser(user :User): CloudResult<Failure, Long> = localUserDataSource.saveUser(user)

  suspend fun getUser(): CloudResult<Failure, User> = localUserDataSource.getCurrentUser()

}