package com.ayesha.mediapp.data.repository

import com.ayesha.core.domain.CloudResult
import com.ayesha.core.domain.Failure
import com.ayesha.mediapp.data.model.ApiResponse
import com.ayesha.mediapp.data.model.RemoteMedication
import com.ayesha.mediapp.data.remote.MedicationDataSource

class MedicationRepository(private val medicationDataSource: MedicationDataSource) {
    suspend fun getMedication(): CloudResult<Failure, RemoteMedication> =
        medicationDataSource.getMedication()

}