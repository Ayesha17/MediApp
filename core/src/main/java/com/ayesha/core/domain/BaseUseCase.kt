package com.ayesha.core.domain

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Suppress("TooGenericExceptionCaught")
abstract class BaseUseCase<in Params, out Type>(private val coroutineDispatcher: CoroutineDispatcher) where Type : Any {

    abstract suspend fun execute(params: Params): CloudResult<Failure, Type>

    protected open suspend fun handleError(ex: Exception): Failure = FailureFactory.create(ex)

    suspend operator fun invoke(params: Params, onResult: (CloudResult<Failure, Type>) -> Unit) {
        withContext(coroutineDispatcher) {
            try {
                val result = execute(params).also { log(it) }
                onResult.invoke(result)
            } catch (ex: Exception) {
                Log.e(TAG,"${ex.message}")
                val failure = handleError(ex)
                onResult.invoke(CloudResult.Error(failure))
            }
        }
    }

    private fun log(cloudResult: CloudResult<Failure, Type>) {
        Log .d(TAG,"${this.javaClass.simpleName}=$cloudResult")
    }

    private companion object {
        private const val TAG = "USECASE_RESULT"
    }
}

suspend operator fun <R : Any> BaseUseCase<Unit, R>.invoke(onResult: (CloudResult<Failure, R>) -> Unit) =
    this(Unit, onResult)
