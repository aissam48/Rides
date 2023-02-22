package com.ibm.rides.remote.repository

import com.ibm.rides.core.utils.Resource
import com.ibm.rides.remote.dto.VehicleModel
import kotlinx.coroutines.flow.Flow

interface RidesService {
    fun getRides(params:HashMap<String, Any>):Flow<Resource<VehicleModel>>
}