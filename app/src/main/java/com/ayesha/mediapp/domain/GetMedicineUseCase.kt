package com.ayesha.mediapp.domain

import com.ayesha.core.domain.BaseUseCase
import com.ayesha.core.domain.CloudResult
import com.ayesha.core.domain.Failure
import com.ayesha.mediapp.data.model.ApiResponse
import com.ayesha.mediapp.data.model.RemoteMedication
import com.ayesha.mediapp.data.repository.MedicationRepository
import kotlinx.coroutines.CoroutineDispatcher


class GetMedicineUseCase(
    private val medicationRepository: MedicationRepository,
    ioDispatcher: CoroutineDispatcher
) : 
    BaseUseCase<Unit, RemoteMedication>(ioDispatcher) {
    override suspend fun execute(params: Unit): CloudResult<Failure, RemoteMedication> =
        medicationRepository.getMedication()
}