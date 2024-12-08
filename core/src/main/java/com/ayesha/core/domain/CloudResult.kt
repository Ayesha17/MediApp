package com.ayesha.core.domain

import androidx.annotation.Keep
import com.ayesha.core.domain.CloudResult.Error
import com.ayesha.core.domain.CloudResult.Success

@Keep
sealed class CloudResult<out Failure, out Data> {

    data class Error<out Failure>(val errorData: Failure) : CloudResult<Failure, Nothing>()
    data class Success<out Data>(val data: Data) : CloudResult<Nothing, Data>()

    val isSuccess get() = this is Success<Data>
    val isError get() = this is Error<Failure>

    fun data(): Data = (this as Success<Data>).data

    fun result(onError: (Failure) -> Unit = {}, onSuccess: (Data) -> Unit = {}): Any =
        when (this) {
            is Error -> onError(errorData)
            is Success -> onSuccess(data)
        }

    suspend fun asyncResult(
        onError: suspend (Failure) -> Unit = {},
        onSuccess: suspend (Data) -> Unit = {},
    ): Any =
        when (this) {
            is Error -> onError(errorData)
            is Success -> onSuccess(data)
        }
}

fun <OldType, Failure, NewType> CloudResult<Failure, OldType>.map(
    transform: (OldType) -> NewType,
): CloudResult<Failure, NewType> {
    return when (this) {
        is Success -> Success(transform(data))
        is Error -> this
    }
}

fun <OldType, Failure, NewType> CloudResult<Failure, OldType>.flatMap(
    transform: (OldType) -> CloudResult<Failure, NewType>,
): CloudResult<Failure, NewType> {
    return when (this) {
        is Success -> transform(data)
        is Error -> this
    }
}
