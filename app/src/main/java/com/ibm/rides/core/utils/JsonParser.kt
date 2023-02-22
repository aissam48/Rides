package com.ibm.rides.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibm.rides.remote.dto.VehicleModel
import com.ibm.rides.remote.dto.VehicleModelItem
import org.json.JSONArray


object JsonParser {
    fun ridesConverter(json: JSONArray): VehicleModel {

        return VehicleModel(
            data = Gson().fromJson<ArrayList<VehicleModelItem>>(
                json.toString(),
                object : TypeToken<ArrayList<VehicleModelItem>>() {}.type
            )
        )
    }


}