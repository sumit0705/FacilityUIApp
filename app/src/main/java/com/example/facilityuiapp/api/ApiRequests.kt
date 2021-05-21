package com.example.facilityuiapp.api

import com.example.facilityuiapp.models.FacilityData
import retrofit2.http.GET

interface ApiRequests {

    @GET("ricky1550/pariksha/db")
    suspend fun getFacilityData(): FacilityData
}