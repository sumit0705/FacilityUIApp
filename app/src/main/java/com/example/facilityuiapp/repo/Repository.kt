package com.example.facilityuiapp.repo

import com.example.facilityuiapp.models.FacilityData
import com.example.facilityuiapp.api.ApiClient
import com.example.facilityuiapp.models.Facility

class Repository {

    suspend fun getFacilityData(): FacilityData {
        return ApiClient.api.getFacilityData()
    }
}