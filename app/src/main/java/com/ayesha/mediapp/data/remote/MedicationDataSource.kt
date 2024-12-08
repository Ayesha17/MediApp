package com.ayesha.mediapp.data.remote

import com.ayesha.core.domain.CloudResult
import com.ayesha.core.domain.Failure
import com.ayesha.mediapp.data.model.ApiResponse
import com.ayesha.mediapp.data.model.RemoteMedication

interface MedicationDataSource {
    suspend fun getMedication(): CloudResult<Failure, RemoteMedication>
}