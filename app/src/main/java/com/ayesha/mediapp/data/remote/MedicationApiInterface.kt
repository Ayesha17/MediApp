package com.ayesha.mediapp.data.remote

import com.ayesha.mediapp.data.model.ApiResponse
import jakarta.inject.Singleton
import retrofit2.http.GET

interface MedicationApiInterface {

    @GET("v3/68a3e042-99d6-4504-8c3d-7363dadfdb3f/")
    suspend fun getMedication(): ApiResponse

}