package com.ayesha.core.domain

import android.util.Log
import com.ayesha.core.domain.Failure.InternetConnectivityFailure
import com.ayesha.core.domain.Failure.UnknownFailure
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

object FailureFactory {

    fun create(ex: Throwable): Failure {
        val failure: Failure =
            if (ex.cause != null && isConnectionFailure(checkNotNull(ex.cause))) {
                InternetConnectivityFailure
            } else {
                UnknownFailure
            }
        Log.d( "Failure", "[${ex.javaClass.simpleName}] -> [$failure]")
        return failure
    }

    private fun isConnectionFailure(ex: Throwable) =
        ex is UnknownHostException || ex is ConnectException || ex is SocketException
}
