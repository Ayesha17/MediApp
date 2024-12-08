package com.ayesha.core.domain

import androidx.annotation.Keep

@Keep
open class Failure {
    data object UnknownFailure : Failure()
    data class UnknownError(val error: String) : Failure()
    data object InternetConnectivityFailure : Failure()
     override fun toString(): String = this.javaClass.simpleName
}
