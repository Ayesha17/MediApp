package com.ayesha.mediapp.domain

import com.ayesha.core.domain.BaseUseCase
import com.ayesha.core.domain.CloudResult
import com.ayesha.core.domain.Failure
import com.ayesha.mediapp.data.database.entities.User
import com.ayesha.mediapp.data.repository.UserRepository
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

class UserAuthUseCase(private val userRepository: UserRepository,ioDispatcher:CoroutineDispatcher) :
    BaseUseCase<User, Long>(ioDispatcher) {
    override suspend fun execute(params: User): CloudResult<Failure, Long> =
         userRepository.addUser(params)
}
