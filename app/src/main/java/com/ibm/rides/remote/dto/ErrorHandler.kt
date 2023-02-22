package com.ibm.rides.remote.dto


data class ErrorHandler(
    var statusCode: Int? = null,
    var message: String? = null,
)
