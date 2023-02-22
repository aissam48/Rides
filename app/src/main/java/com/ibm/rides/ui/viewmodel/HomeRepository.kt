package com.ibm.rides.ui.viewmodel

import com.ibm.rides.core.utils.Resource
import com.ibm.rides.remote.dto.VehicleModel
import com.ibm.rides.remote.service.RidesServiceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(private val ridesServiceImpl: RidesServiceImpl) {

    fun onGetRides(params: HashMap<String, Any>): Flow<Resource<VehicleModel>> = flow {
        ridesServiceImpl.getRides(params)
            .collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        emit(Resource.Loading())
                    }
                    is Resource.Error -> {
                        emit(Resource.Error(result.message, result.errorBody))
                    }
                    is Resource.Success -> {
                        emit(Resource.Success(result.data))
                    }
                }
            }
    }
}