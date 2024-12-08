package com.ayesha.mediapp.data.remote

import com.ayesha.core.domain.CloudResult
import com.ayesha.core.domain.Failure
import com.ayesha.mediapp.data.database.dao.UserDao
import com.ayesha.mediapp.data.database.entities.User

class LocalUserDataSource(private val userDao: UserDao) : UserDataSource {
    override suspend fun getCurrentUser(): CloudResult<Failure, User> =
        CloudResult.Success(userDao.readLoginData())


    override suspend fun saveUser(user: User): CloudResult<Failure, Long> =
        CloudResult.Success(userDao.insertUser(user))
}