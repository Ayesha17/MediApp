package com.ayesha.mediapp.domain

import com.ayesha.core.domain.BaseUseCase
import com.ayesha.core.domain.CloudResult
import com.ayesha.core.domain.Failure
import com.ayesha.mediapp.data.database.entities.User
import com.ayesha.mediapp.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetUserUseCase (private val userRepository: UserRepository, ioDispatcher: CoroutineDispatcher) :
    BaseUseCase<Unit, User>(ioDispatcher) {
    override suspend fun execute(params: Unit): CloudResult<Failure, User> =
        userRepository.getUser()
}

