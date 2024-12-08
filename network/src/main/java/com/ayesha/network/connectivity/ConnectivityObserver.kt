package com.ayesha.network.connectivity

import androidx.annotation.Keep
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun isConnected(): Boolean

    fun observe(): Flow<Status>

    @Keep
    enum class Status {
        Available,
        Unavailable,
        Lost,
    }
}