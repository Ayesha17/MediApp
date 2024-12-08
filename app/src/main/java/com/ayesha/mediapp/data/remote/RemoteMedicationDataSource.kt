package com.ayesha.mediapp.data.remote

import com.ayesha.core.domain.CloudResult
import com.ayesha.core.domain.Failure
import com.ayesha.mediapp.data.model.ApiResponse
import com.ayesha.mediapp.data.model.RemoteMedication

class RemoteMedicationDataSource(val medicationApiInterface: MedicationApiInterface,val medicationMapper: MedicationMapper) :MedicationDataSource{
    override suspend fun getMedication(): CloudResult<Failure, RemoteMedication> {
       val result=  medicationApiInterface.getMedication()
       return CloudResult.Success(medicationMapper.map(result))
    }
}