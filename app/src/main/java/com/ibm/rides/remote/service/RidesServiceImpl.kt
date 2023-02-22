package com.ibm.rides.remote.service

import com.ibm.rides.core.utils.ApiField
import com.ibm.rides.remote.HttpRoutes
import com.ibm.rides.remote.repository.RidesService
import com.ibm.rides.core.utils.Resource
import com.ibm.rides.core.utils.ClientApiManager
import com.ibm.rides.core.utils.JsonParser
import com.ibm.rides.remote.dto.VehicleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RidesServiceImpl @Inject constructor(private val apiManager: ClientApiManager) :
    RidesService {
    override fun getRides(params: HashMap<String, Any>): Flow<Resource<VehicleModel>> = flow {
        emit(Resource.Loading())

        val parameterFormData = arrayListOf<Pair<String, Any>>()
        parameterFormData.add(Pair(ApiField.SIZE, params[ApiField.SIZE] ?: 1))

        apiManager.makeRequest(
            parameterFormData = parameterFormData,
            failureCallback = { error ->
                emit(Resource.Error(error.message, error.errorBody))
            },
            successCallback = {
                emit(Resource.Success(JsonParser.ridesConverter(it)))
            }
        )

    }
}